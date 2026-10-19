import type {
    ArtworkDetails,
    ArtworkStats,
    ArtworkStatsSummary,
    BoardSubmission,
    Card,
    GameMode,
    GameSummary,
    GuessResult,
    HintStats,
    Session,
    SessionResult,
    SourceKey,
} from './types'

export class ApiError extends Error {
    constructor(
        readonly status: number,
        message: string,
    ) {
        super(message)
    }
}

async function request<T>(
    method: string,
    path: string,
    body?: unknown,
): Promise<T> {
    const response = await fetch(`/api/v1${path}`, {
        method,
        headers:
            body === undefined
                ? undefined
                : { 'Content-Type': 'application/json' },
        body: body === undefined ? undefined : JSON.stringify(body),
    })
    const text = await response.text()
    if (!response.ok)
        throw new ApiError(response.status, text || response.statusText)
    return (text ? JSON.parse(text) : undefined) as T
}

export const api = {
    startGame: (gameMode: GameMode, source: SourceKey) =>
        request<Card[]>(
            'POST',
            `/game/start?${new URLSearchParams({ gameMode, source })}`,
        ),
    submitBoard: (gameId: number, submission: BoardSubmission) =>
        request<void>('POST', `/game/${gameId}/submit`, submission),
    rateGame: (gameId: number, rating: boolean) =>
        request<void>('PATCH', '/game/game-quality/submit', { gameId, rating }),
    listGames: () => request<GameSummary[]>('GET', '/game/list'),

    startSession: (gameId: number) =>
        request<Session>('POST', `/session/${gameId}/start`),
    randomGameId: (gameMode: GameMode) =>
        request<number>('POST', `/session/start/random/${gameMode}`),
    guess: (sessionId: string, cardId: string) =>
        request<GuessResult>('POST', `/session/${sessionId}/guess/${cardId}`),
    finishSession: (sessionId: string) =>
        request<SessionResult>('POST', `/session/${sessionId}/finish`),

    artworkDetails: (id: string) =>
        request<ArtworkDetails>('GET', `/artwork/${id}/details`),
    artworkDetailsBatch: (ids: string[]) =>
        request<ArtworkDetails[]>(
            'GET',
            `/artwork/details?ids=${ids.join(',')}`,
        ),
    artworkStatsList: () =>
        request<ArtworkStatsSummary[]>('GET', '/artwork/statslist'),
    artworkStats: (id: string) =>
        request<ArtworkStats>('GET', `/artwork/${id}/stats`),

    hintStats: () => request<HintStats[]>('GET', '/hints/stats'),
}
