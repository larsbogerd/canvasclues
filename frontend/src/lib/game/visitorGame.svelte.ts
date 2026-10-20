import { SvelteMap } from 'svelte/reactivity'
import { api } from '#lib/api/client.ts'
import { prefetchArtworkDetails } from '#lib/api/artworkDetails.ts'
import type { Card, Hint } from '#lib/api/types.ts'
import { MAX_FORGED_HITS, nextCardValue } from '#lib/game/rules.ts'

export type Reveal = 'right' | 'wrong' | 'forged'

const FINISH_DELAY_MS = 800

export class VisitorGame {
    status: 'loading' | 'error' | 'playing' | 'finished' = $state('loading')
    sessionId = ''
    cards: Card[] = $state([])
    hint: Hint | null = $state(null)
    picksToFind = $state(0)
    reveals = new SvelteMap<string, Reveal>()
    score = $state(0)
    streak = $state(0)
    forgedHits = $state(0)
    guessing = $state(false)

    found = $derived(
        [...this.reveals.values()].filter((r) => r === 'right').length,
    )
    nextValue = $derived(nextCardValue(this.streak))
    gameOver = $derived(this.forgedHits >= MAX_FORGED_HITS)

    constructor(private readonly gameId: number) {}

    async load() {
        this.status = 'loading'
        this.reveals.clear()
        try {
            const session = await api.startSession(this.gameId)
            this.sessionId = session.sessionId
            this.cards = session.cards
            this.hint = session.hint
            this.picksToFind = session.spymasterPickCount
            this.score = 0
            this.streak = 0
            this.forgedHits = 0
            void prefetchArtworkDetails(
                this.cards.map((card) => card.artworkId),
            )
            this.status = 'playing'
        } catch (error) {
            console.error(error)
            this.status = 'error'
        }
    }

    async guess(cardId: string) {
        if (
            this.status !== 'playing' ||
            this.guessing ||
            this.reveals.has(cardId)
        )
            return
        this.guessing = true
        try {
            const result = await api.guess(this.sessionId, cardId)
            this.reveals.set(
                cardId,
                result.correct
                    ? 'right'
                    : result.cardType === 'ASSASSIN'
                      ? 'forged'
                      : 'wrong',
            )
            this.score = result.score
            this.streak = result.comboStreak
            this.forgedHits = result.assassinGuesses
        } catch (error) {
            console.error(error)
        } finally {
            this.guessing = false
        }
        // The back-end closes the session itself on the last forged hit; finishing it again is a 409.
        if (this.gameOver)
            setTimeout(() => {
                if (this.status === 'playing') this.status = 'finished'
            }, FINISH_DELAY_MS)
        else if (this.found === this.picksToFind)
            setTimeout(() => void this.finish(), FINISH_DELAY_MS)
    }

    async finish() {
        if (this.status !== 'playing') return
        this.status = 'finished'
        try {
            this.score = (await api.finishSession(this.sessionId)).score
        } catch (error) {
            console.error(error)
        }
    }

    async rate(good: boolean) {
        try {
            await api.rateGame(this.gameId, good)
        } catch (error) {
            console.error(error)
        }
    }
}
