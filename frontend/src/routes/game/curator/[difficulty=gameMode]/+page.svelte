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
    import { CuratorGame } from '#lib/game/curatorGame.svelte.ts'
    import { selectedSource } from '#lib/selectedSource.svelte.ts'
    import type { PageProps } from './$types'

    let { params }: PageProps = $props()

    // svelte-ignore state_referenced_locally
    const game = new CuratorGame(params.difficulty, selectedSource.value)
    let activeCard = $state<Card | null>(null)

    onMount(() => void game.load())

    function newBoard() {
        activeCard = null
        void game.load()
    }
</script>

<PlayLayout
    phase="Curator"
    {activeCard}
    credit={game.status === 'loading' ? null : (game.cards[0]?.source ?? null)}
>
    {#if game.status === 'loading' || game.status === 'error'}
        <BoardStatus
            state={game.status}
            title="Het museum reageert niet"
            message="De kunstwerken konden niet worden opgehaald. Probeer het opnieuw, of kies een ander museum."
            onretry={() => void game.load()}
        />
    {:else}
        <Board cards={game.cards}>
            {#snippet card(card: Card)}
                <BoardCard
                    {card}
                    forged={card.type === 'ASSASSIN'}
                    selected={game.picks.has(card.id)}
                    infoShown={activeCard?.id === card.id}
                    onclick={() => {
                        if (card.type !== 'ASSASSIN') game.toggle(card.id)
                    }}
                    oninfo={() => (activeCard = card)}
                />
            {/snippet}
        </Board>
    {/if}

    {#snippet sidebar()}
        <Panel>
            {#snippet header()}
                <ScoreBox score={game.score} />
            {/snippet}
            <form
                class="hint-form"
                onsubmit={(event) => {
                    event.preventDefault()
                    void game.publish()
                }}
            >
                {#if game.message}
                    <p class="message" role="alert">{game.message}</p>
                {/if}
                <input
                    bind:value={game.hint}
                    oninput={() => (game.message = null)}
                    placeholder="Jouw hint."
                    aria-label="Jouw hint"
                    autocomplete="off"
                    spellcheck="false"
                    disabled={game.status !== 'ready'}
                />
                <button
                    type="submit"
                    disabled={game.status !== 'ready' || game.submitting}
                    >Bevestig hint</button
                >
            </form>
        </Panel>
        <GameRules title="Curator fase">
            <p>
                Als curator geef je een hint die meerdere kunstwerken verbindt
                en leid je bezoekers naar de juiste kaarten, zonder de vervalste
                (vervaagde) kunstwerken te raken.
            </p>
            <p>
                Tijdens de bezoekersfase zijn de vervalste kunstwerken niet
                zichtbaar. Bezoekers vertrouwen volledig op jouw hints. Raakt de
                bezoeker twee vervalste kaarten, dan eindigt het spel.
            </p>
            <p>
                Elke juiste kaart is 20 punten waard: meer kaarten per hint
                betekent hogere score én meer risico.
            </p>
        </GameRules>
    {/snippet}
</PlayLayout>

<Modal
    bind:open={
        () => game.status === 'published',
        (open) => {
            if (!open) newBoard()
        }
    }
>
    <section class="result">
        <h1 class="modal-title">Puzzel gepubliceerd!</h1>
        <div class="modal-stat modal-stat--solo">
            <div class="modal-stat-value score">{game.score}</div>
            <div class="modal-stat-label">Score</div>
        </div>
        <div class="modal-body modal-body--centered">
            <p>
                Bezoekers kunnen nu jouw puzzel spelen. Bekijk later hoe goed
                jouw hints werken.
            </p>
        </div>
        <hr class="modal-divider" />
        <div class="modal-actions">
            <Button onclick={() => goto(resolve('/game/bezoeker'))}
                >Speel als Bezoeker</Button
            >
            <Button onclick={newBoard}>Nieuwe puzzel</Button>
        </div>
    </section>
</Modal>

<style>
    .hint-form {
        display: flex;
        flex-direction: column;
        gap: 12px;
        padding: 24px;
        font-family: var(--font-secondary), sans-serif;
    }

    .message {
        margin: 0;
        color: var(--game-wrong);
    }

    input {
        width: 100%;
        height: 3rem;
        box-sizing: border-box;
        padding: 12px 16px;
        font: inherit;
        color: var(--text-primary);
        background: var(--color-secondary);
        border: 1px solid var(--secondary-border);
        border-radius: 20px;
        outline: none;
    }

    input::placeholder {
        color: var(--text-placeholder);
    }

    input:focus {
        border-color: var(--color-primary);
    }

    input:focus::placeholder {
        color: transparent;
    }

    button {
        width: 100%;
        padding: 12px;
        font: inherit;
        font-weight: bold;
        color: var(--color-bg-base);
        background-color: var(--color-primary);
        border: none;
        border-radius: 20px;
        cursor: pointer;
        box-shadow: 0 10px 20px
            color-mix(in srgb, var(--color-primary) 28%, transparent);
        transition:
            box-shadow 180ms ease,
            opacity 180ms ease;
    }

    button:hover:not(:disabled),
    button:focus-visible {
        box-shadow: 0 14px 24px
            color-mix(in srgb, var(--color-primary) 38%, transparent);
    }

    button:disabled {
        opacity: 0.6;
        cursor: default;
    }

    .result {
        width: 28vw;
        min-width: 280px;
        padding: clamp(24px, 3vw, 48px) clamp(32px, 4vw, 64px);
    }
</style>
