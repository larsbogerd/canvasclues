import { api } from './client'
import type { ArtworkDetails } from './types'

const cache = new Map<string, ArtworkDetails>()

export async function getArtworkDetails(id: string): Promise<ArtworkDetails> {
    const cached = cache.get(id)
    if (cached) return cached
    const details = await api.artworkDetails(id)
    cache.set(id, details)
    return details
}

export async function prefetchArtworkDetails(ids: string[]): Promise<void> {
    const missing = ids.filter((id) => id && !cache.has(id))
    if (missing.length === 0) return
    try {
        for (const details of await api.artworkDetailsBatch(missing))
            cache.set(details.id, details)
    } catch (error) {
        console.warn('Prefetching artwork details failed', error)
    }
}

export function htmlToText(html: string | null): string {
    if (!html) return ''
    const withBreaks = html
        .replace(/<\/p>/gi, '\n\n')
        .replace(/<br\s*\/?>/gi, '\n')
    const doc = new DOMParser().parseFromString(withBreaks, 'text/html')
    return (doc.body.textContent ?? '').trim()
}
