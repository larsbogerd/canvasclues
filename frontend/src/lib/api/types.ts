export type SourceKey = 'artic' | 'cma' | 'met'
export const GAME_MODES = ['makkelijk', 'gemiddeld', 'moeilijk'] as const
export type GameMode = (typeof GAME_MODES)[number]
export type CardType = 'PLAYABLE' | 'ASSASSIN'

export interface Card {
    id: string
    gameId: number
    type: CardType | null
    imageUrlTemplate: string
    title: string
    artistDisplay: string
    artworkId: string
    altText: string
    source: SourceKey
}

export interface Hint {
    id: string
    gameId: number
    content: string
}

export interface Session {
    sessionId: string
    cards: Card[]
    hint: Hint
    spymasterPickCount: number
}

export interface GuessResult {
    correct: boolean
    score: number
    comboStreak: number
    wrongGuesses: number
    assassinGuesses: number
    cardType: CardType
}

export interface SessionResult {
    score: number
    wrongGuesses: number
    assassinGuesses: number
}

export interface GameSummary {
    gameId: number
    hint: string
    spyScore: number
    createdAt: string
    playCount: number
    qualityRatio: number | null
    gameMode: GameMode
    source: SourceKey
}

export interface BoardSubmission {
    cardIds: string[]
    spyScore: number
    hintContent: string
}

export interface ArtworkDetails {
    id: string
    dateDisplay: string | null
    mediumDisplay: string | null
    placeOfOrigin: string | null
    departmentTitle: string | null
    dimensions: string | null
    styleTitle: string | null
    artworkTypeTitle: string | null
    shortDescription: string | null
}

export interface ArtworkStatsSummary {
    id: string
    imageUrlTemplate: string
    title: string
    artistDisplay: string
    timesLoaded: number
    timesSpymasterPick: number
    timesCorrectGuess: number
    timesBadGuess: number
    pickPercentage: number | null
    guessPercentage: number | null
    firstUsedAt: string | null
    lastUsedAt: string | null
}

export interface ArtworkStats extends ArtworkStatsSummary {
    dateDisplay: string | null
    mediumDisplay: string | null
    placeOfOrigin: string | null
    dimensions: string | null
    departmentTitle: string | null
}

export interface HintStats {
    hint: string
    frequency: number
}
