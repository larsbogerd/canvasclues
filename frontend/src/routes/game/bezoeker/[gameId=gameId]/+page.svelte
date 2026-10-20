<script lang="ts">
    import { goto } from '$app/navigation'
    import { resolve } from '$app/paths'
    import { onMount } from 'svelte'
    import Board from '#lib/components/board/Board.svelte'
    import BoardCard from '#lib/components/board/BoardCard.svelte'
    import BoardStatus from '#lib/components/board/BoardStatus.svelte'
    import GameRules from '#lib/components/play/GameRules.svelte'
    import Panel from '#lib/components/play/Panel.svelte'
    import PlayLayout from '#lib/components/play/PlayLayout.svelte'
    import ScoreBox from '#lib/components/play/ScoreBox.svelte'
    import Button from '#lib/components/ui/Button.svelte'
    import Modal from '#lib/components/ui/Modal.svelte'
    import type { Card } from '#lib/api/types.ts'
    import { VisitorGame } from '#lib/game/visitorGame.svelte.ts'
    import thumbDown from '#lib/images/thumb-down.svg'
    import thumbUp from '#lib/images/thumb-up.svg'
    import type { PageProps } from './$types'

    let { params }: PageProps = $props()

    // svelte-ignore state_referenced_locally
    const game = new VisitorGame(params.gameId)
    let selectedId = $state<string | null>(null)
    let activeCard = $state<Card | null>(null)
    let resultOpen = $state(false)
    let rated = $state(false)

    onMount(() => void game.load())

    $effect(() => {
        if (game.status === 'finished') resultOpen = true
    })

    function select(card: Card) {
        activeCard = card
        if (!game.reveals.has(card.id))
            selectedId = selectedId === card.id ? null : card.id
    }

    async function lockIn(cardId: string) {
        await game.guess(cardId)
        selectedId = null
    }

    async function rate(good: boolean) {
        rated = true
        await game.rate(good)
    }
</script>

<PlayLayout
    phase="Bezoeker"
    {activeCard}
    credit={game.status === 'loading' ? null : (game.cards[0]?.source ?? null)}
>
    {#if game.status === 'loading' || game.status === 'error'}
        <BoardStatus
            state={game.status}
            title="Deze puzzel laadt niet"
            message="Deze puzzel kon niet worden geladen. Probeer het opnieuw."
            onretry={() => void game.load()}
        />
    {:else}
        <Board cards={game.cards}>
            {#snippet card(card: Card)}
                <BoardCard
                    {card}
                    selected={selectedId === card.id}
                    infoShown={activeCard?.id === card.id}
                    reveal={game.reveals.get(card.id)}
                    onclick={() => select(card)}
                    onlockin={game.status === 'playing' && !game.guessing
                        ? () => void lockIn(card.id)
                        : undefined}
                />
            {/snippet}
        </Board>
    {/if}

    {#snippet sidebar()}
        <Panel>
            {#snippet header()}
                <span class="hint">{game.hint?.content ?? ''}</span>
            {/snippet}
            <div class="progress">
                <div class="stats">
                    <div class="stat">
                        <span class="stat-value">{game.found}</span>
                        <span class="stat-label">gevonden</span>
                    </div>
                    <div class="stat">
                        <span class="stat-value score">+{game.nextValue}</span>
                        <span class="stat-label">volgende</span>
                    </div>
                </div>
                <ScoreBox score={game.score} />
                <Button
                    variant="secondary"
                    disabled={game.status !== 'playing'}
                    onclick={() => void game.finish()}>Beëindig poging</Button
                >
            </div>
        </Panel>
        <GameRules title="Bezoeker fase">
            <p>
                Als bezoeker raad je kunstwerken op basis van de hint van de
                curator en kies je de juiste kaarten.
            </p>
            <p>
                Elke correcte gok levert 20 punten op, vermenigvuldigd met je
                combo multiplier. Hoe langer je reeks, hoe hoger de multiplier.
            </p>
            <p>
                Raak je een kaart die niet door de curator is geselecteerd, dan
                reset je je multiplier, maar het spel gaat door.
            </p>
            <p>
                Er zitten ook 4 vervalste kunstwerken in het spel. Raak je er
                daarvan twee, dan is het game over.
            </p>
        </GameRules>
    {/snippet}
</PlayLayout>

<Modal bind:open={resultOpen}>
    <section class="result">
        {#if !rated}
            <h1 class="modal-title">Beoordeel Board</h1>
            <h2 class="modal-sub-title">
                Vond je dat de hint goed bij de plaatjes paste? Laat het ons
                weten.
            </h2>
            <div class="modal-stats">
                <button
                    type="button"
                    class="modal-stat rate"
                    onclick={() => rate(true)}
                >
                    <img class="modal-stat-value" src={thumbUp} alt="" />
                    <span class="modal-stat-label">Goed Board</span>
                </button>
                <button
                    type="button"
                    class="modal-stat rate"
                    onclick={() => rate(false)}
                >
                    <img class="modal-stat-value" src={thumbDown} alt="" />
                    <span class="modal-stat-label">Slecht Board</span>
                </button>
            </div>
            <hr class="modal-divider" />
            <p class="modal-detail">
                Het beoordelen van de hint kwaliteit is noodzakelijk voor het
                graderen van legitieme/interessante hints.
            </p>
        {:else}
            {#if game.gameOver}
                <h1 class="modal-title">Game Over!</h1>
                <p class="modal-detail">
                    Je hebt 2 vervalste kunstwerken geraakt. Het spel eindigt
                    hier.
                </p>
            {:else}
                <h1 class="modal-title">Clue voltooid!</h1>
            {/if}
            <div class="modal-stats">
                <div class="modal-stat">
                    <div class="modal-stat-value">
                        {game.found}/{game.picksToFind}
                    </div>
                    <div class="modal-stat-label">Gevonden</div>
                </div>
                <div class="modal-stat">
                    <div class="modal-stat-value score">{game.score}</div>
                    <div class="modal-stat-label">Score</div>
                </div>
            </div>
            <hr class="modal-divider" />
            <div class="modal-actions">
                <Button onclick={() => goto(resolve('/game'))}
                    >Speel als curator</Button
                >
                <Button onclick={() => goto(resolve('/game/bezoeker'))}
                    >Nieuwe clue</Button
                >
            </div>
        {/if}
    </section>
</Modal>

<style>
    .hint {
        font-family: var(--font-secondary), sans-serif;
        font-size: clamp(1.4rem, 2.5vw, 2.2rem);
        font-weight: 800;
        text-transform: uppercase;
        letter-spacing: 0.06em;
        overflow-wrap: anywhere;
    }

    .progress {
        display: flex;
        flex-direction: column;
        align-items: center;
        gap: 14px;
        padding: 24px;
    }

    .progress > :global(*) {
        width: 80%;
    }

    .progress > :global(button) {
        width: 100%;
        margin-top: 2px;
        padding: 12px;
        font-size: 16px;
    }

    .stats {
        display: flex;
        gap: 7px;
    }

    .stat {
        flex: 1;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        gap: 9px;
        min-height: 82px;
        padding: 12px;
        border-radius: 12px;
        background-color: var(--color-bg-base);
        font-family: var(--font-secondary), sans-serif;
    }

    .stat-value {
        font-size: clamp(1.4rem, 2vw, 2rem);
        font-weight: 600;
        line-height: 1.2;
        color: var(--text-primary);
    }

    .stat-label {
        color: var(--text-eyebrow);
        text-transform: uppercase;
        font-size: 12px;
    }

    .score {
        color: var(--color-primary);
    }

    .result {
        width: 28vw;
        min-width: 280px;
        padding: clamp(24px, 3vw, 48px) clamp(32px, 4vw, 64px);
    }

    .rate {
        border: none;
        cursor: pointer;
        background-color: var(--color-primary);
        transition: transform 180ms ease;
    }

    .rate:hover,
    .rate:focus-visible {
        transform: scale(1.05);
    }

    .rate .modal-stat-label {
        display: block;
        color: var(--text-art-details);
    }

    .rate img {
        height: 2.4rem;
    }
</style>
