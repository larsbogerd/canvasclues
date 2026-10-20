import { SvelteSet } from 'svelte/reactivity'
import { ApiError, api } from '#lib/api/client.ts'
import { prefetchArtworkDetails } from '#lib/api/artworkDetails.ts'
import type { Card, GameMode, SourceKey } from '#lib/api/types.ts'
import { CARD_POINTS, MIN_CURATOR_PICKS, hintError } from '#lib/game/rules.ts'

export class CuratorGame {
    status: 'loading' | 'error' | 'ready' | 'published' = $state('loading')
    cards: Card[] = $state([])
    picks = new SvelteSet<string>()
    hint = $state('')
    message: string | null = $state(null)
    submitting = $state(false)

    score = $derived(this.picks.size * CARD_POINTS)

    constructor(
        private readonly mode: GameMode,
        private readonly source: SourceKey,
    ) {}

    async load() {
        this.status = 'loading'
        this.picks.clear()
        this.hint = ''
        this.message = null
        try {
            this.cards = await api.startGame(this.mode, this.source)
            void prefetchArtworkDetails(
                this.cards.map((card) => card.artworkId),
            )
            this.status = 'ready'
        } catch (error) {
            console.error(error)
            this.status = 'error'
        }
    }

    toggle(cardId: string) {
        if (this.picks.has(cardId)) this.picks.delete(cardId)
        else this.picks.add(cardId)
    }

    async publish() {
        this.message =
            hintError(this.hint) ??
            (this.picks.size < MIN_CURATOR_PICKS
                ? `Kies minstens ${MIN_CURATOR_PICKS} kunstwerken.`
                : null)
        if (this.message || this.submitting) return

        this.submitting = true
        try {
            await api.submitBoard(this.cards[0].gameId, {
                cardIds: [...this.picks],
                spyScore: this.picks.size,
                hintContent: this.hint,
            })
            this.status = 'published'
        } catch (error) {
            this.message =
                error instanceof ApiError && error.status < 500
                    ? error.message
                    : 'Publiceren mislukt. Probeer het opnieuw.'
        } finally {
            this.submitting = false
        }
    }
}
