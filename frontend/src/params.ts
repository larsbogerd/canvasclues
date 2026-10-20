import { defineParams } from '@sveltejs/kit/params'
import { GAME_MODES, type GameMode } from '#lib/api/types.ts'

export const params = defineParams({
    gameMode: (param) =>
        GAME_MODES.find((mode): mode is GameMode => mode === param),
    gameId: (param) => (/^\d+$/.test(param) ? Number(param) : undefined),
})
