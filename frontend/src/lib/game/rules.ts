export const CARD_POINTS = 20
export const MIN_CURATOR_PICKS = 2
export const MAX_FORGED_HITS = 2

const STREAK_MULTIPLIERS = [1, 1.5, 2, 3]

// Mirrors ScoreService.getMultiplier: the guess that extends a streak of n scores at n + 1.
export function nextCardValue(streak: number): number {
    return (
        CARD_POINTS *
        STREAK_MULTIPLIERS[Math.min(streak, STREAK_MULTIPLIERS.length - 1)]
    )
}

// Same rule as HintService.validateHint, checked here first for a friendlier message.
export function hintError(hint: string): string | null {
    if (/^[a-zA-Z]+$/.test(hint)) return null
    if (hint.trim() === '') return 'Vul eerst een hint in.'
    if (/\s/.test(hint.trim())) return 'Een hint mag maar één woord bevatten.'
    return 'Een hint mag alleen letters bevatten.'
}
