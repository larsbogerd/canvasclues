import type { SourceKey } from '#lib/api/types.ts'
import { SOURCE_KEYS } from '#lib/sources.ts'

const STORAGE_KEY = 'canvasclues.source'

function stored(): SourceKey {
    try {
        const value = localStorage.getItem(STORAGE_KEY)
        return SOURCE_KEYS.find((key) => key === value) ?? 'cma'
    } catch {
        return 'cma'
    }
}

let current = $state<SourceKey>(stored())

export const selectedSource = {
    get value() {
        return current
    },
    set value(key: SourceKey) {
        current = key
        try {
            localStorage.setItem(STORAGE_KEY, key)
        } catch {
            // Storage blocked (private mode): the choice lasts for this visit only.
        }
    },
}
