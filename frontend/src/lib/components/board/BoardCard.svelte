<script lang="ts">
    import type { Card } from '#lib/api/types.ts'
    import ArtworkImage from '#lib/components/ui/ArtworkImage.svelte'
    import type { Reveal } from '#lib/game/visitorGame.svelte.ts'
    import infoIcon from '#lib/images/info.svg'
    import { imageUrl } from '#lib/imageUrl.ts'
    import ForgedLabel from './ForgedLabel.svelte'

    let {
        card,
        selected,
        infoShown,
        forged = false,
        reveal,
        onclick,
        oninfo,
        onlockin,
    }: {
        card: Card
        selected: boolean
        infoShown: boolean
        forged?: boolean
        reveal?: Reveal
        onclick: () => void
        oninfo?: () => void
        onlockin?: () => void
    } = $props()
</script>

<div
    class="card {reveal ? `reveal-${reveal}` : ''}"
    class:selected
    class:forged
    class:info-shown={infoShown}
    role="button"
    tabindex={forged || reveal ? -1 : 0}
    aria-pressed={selected}
    aria-label={card.title}
    {onclick}
    onkeydown={(event) => {
        if (event.target === event.currentTarget && event.key === 'Enter')
            onclick()
    }}
>
    <ArtworkImage src={imageUrl(card.imageUrlTemplate)} alt={card.altText} />

    {#if forged}
        <ForgedLabel />
    {/if}

    {#if oninfo}
        <button
            type="button"
            class="info"
            aria-label="Info over {card.title}"
            onclick={(event) => {
                event.stopPropagation()
                oninfo()
            }}
        >
            <img src={infoIcon} alt="" />
        </button>
    {/if}

    {#if onlockin && !reveal}
        <button
            type="button"
            class="lock-in"
            tabindex={selected ? 0 : -1}
            onclick={(event) => {
                event.stopPropagation()
                onlockin()
            }}
        >
            <svg
                viewBox="0 0 200 50"
                preserveAspectRatio="none"
                aria-hidden="true"
            >
                <path
                    d="M36 0H164A6 6 0 0 1 170 6V10A15 15 0 0 1 170 40V44A6 6 0 0 1 164 50H36A6 6 0 0 1 30 44V40A15 15 0 0 1 30 10V6A6 6 0 0 1 36 0Z"
                    fill="currentColor"
                />
            </svg>
            <span>Bevestig</span>
        </button>
    {/if}
</div>

<style>
    .card {
        --ring: clamp(4px, 1vh, 12px);
        width: 100%;
        aspect-ratio: 1;
        overflow: hidden;
        border-radius: 15%;
        user-select: none;
        position: relative;
        cursor: pointer;
        outline: none;
        box-shadow: inset 0 0 0 0 transparent;
        transition:
            box-shadow 120ms cubic-bezier(0.2, 0.8, 0.2, 1),
            transform 120ms cubic-bezier(0.2, 0.8, 0.2, 1);
    }

    /* The ring is an inset shadow on top of the image (::after), so selecting a card never
       changes its layout box the way a border would. */
    .card::after {
        content: '';
        position: absolute;
        inset: 0;
        border-radius: inherit;
        box-shadow: inset 0 0 0 var(--ring-width, 0)
            var(--ring-color, transparent);
        transition: box-shadow 120ms cubic-bezier(0.2, 0.8, 0.2, 1);
        pointer-events: none;
    }

    .card:focus-visible::after {
        --ring-width: 3px;
        --ring-color: var(--color-primary);
    }

    .selected {
        box-shadow: 7px 6px 28px 1px var(--active-card-shadow);
    }

    .selected::after {
        --ring-width: var(--ring);
        --ring-color: var(--color-primary);
    }

    .forged {
        cursor: not-allowed;
    }

    .reveal-right,
    .reveal-wrong,
    .reveal-forged {
        cursor: default;
        animation: reveal 380ms cubic-bezier(0.2, 0.8, 0.2, 1);
    }

    .reveal-right::after {
        --ring-width: var(--ring);
        --ring-color: var(--game-good);
    }

    .reveal-wrong::after {
        --ring-width: var(--ring);
        --ring-color: var(--game-wrong);
    }

    .reveal-forged {
        background: #000;
    }

    .reveal-forged::after {
        --ring-width: var(--ring);
        --ring-color: #000;
    }

    .reveal-forged :global(img) {
        opacity: 0.15;
    }

    @keyframes reveal {
        40% {
            transform: scale(0.94);
        }
    }

    .info {
        position: absolute;
        top: 5%;
        right: 5%;
        z-index: 2;
        background: var(--bg-light-opacity);
        border: none;
        border-radius: 50%;
        backdrop-filter: blur(4px);
        width: clamp(24px, 15%, 48px);
        height: clamp(24px, 15%, 48px);
        display: flex;
        align-items: center;
        justify-content: center;
        cursor: pointer;
        opacity: 0;
        transition: opacity 200ms ease 500ms;
    }

    .card:hover .info,
    .info:focus-visible,
    .info-shown .info {
        opacity: 1;
        transition: opacity 200ms ease 100ms;
    }

    .info-shown .info {
        background: var(--color-primary);
    }

    .info img {
        width: 50%;
        height: 50%;
    }

    .info-shown .info img {
        filter: brightness(0) invert(1);
    }

    .lock-in {
        position: absolute;
        bottom: 8%;
        left: 50%;
        transform: translateX(-50%);
        z-index: 2;
        width: clamp(100px, 80%, 300px);
        height: clamp(32px, 18%, 64px);
        container-type: inline-size;
        padding: 0;
        display: flex;
        align-items: center;
        justify-content: center;
        background: none;
        border: none;
        cursor: pointer;
        color: var(--color-tertiary);
        opacity: 0;
        pointer-events: none;
        transition:
            opacity 160ms ease,
            color 160ms ease;
    }

    .selected .lock-in {
        opacity: 1;
        pointer-events: auto;
    }

    .lock-in:hover,
    .lock-in:focus-visible {
        color: var(--color-tertiary-active);
        outline: none;
    }

    .lock-in svg {
        position: absolute;
        inset: 0;
        width: 100%;
        height: 100%;
        filter: drop-shadow(0 3px 6px rgb(0 0 0 / 0.35));
    }

    .lock-in span {
        position: relative;
        color: var(--color-secondary);
        font-family: var(--font-display), sans-serif;
        font-size: 14cqw;
        letter-spacing: 0.08em;
        text-transform: uppercase;
        text-shadow: 0 1px 2px rgb(0 0 0 / 0.35);
    }

    @media (prefers-reduced-motion: reduce) {
        .card {
            animation: none;
        }
    }
</style>
