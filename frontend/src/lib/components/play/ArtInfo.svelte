<script lang="ts">
    import { getArtworkDetails, htmlToText } from '#lib/api/artworkDetails.ts'
    import type { ArtworkDetails, Card } from '#lib/api/types.ts'
    import ArtworkImage from '#lib/components/ui/ArtworkImage.svelte'
    import Modal from '#lib/components/ui/Modal.svelte'
    import { imageUrl } from '#lib/imageUrl.ts'

    let { card }: { card: Card } = $props()

    let expanded = $state(false)
    let details = $state<ArtworkDetails | null>(null)

    const src = $derived(imageUrl(card.imageUrlTemplate))
    const description = $derived(htmlToText(details?.shortDescription ?? null))
    const traits = $derived(
        details
            ? [
                  ['Datum', details.dateDisplay],
                  ['Techniek', details.mediumDisplay],
                  ['Herkomst', details.placeOfOrigin],
                  ['Afdeling', details.departmentTitle],
                  ['Afmetingen', details.dimensions],
                  ['Stijl', details.styleTitle],
                  ['Type', details.artworkTypeTitle],
              ].filter((trait): trait is [string, string] => !!trait[1])
            : [],
    )

    async function expand() {
        details = null
        expanded = true
        try {
            details = await getArtworkDetails(card.artworkId)
        } catch (error) {
            console.error(error)
        }
    }
</script>

<section class="art-info">
    <header>
        <div>
            <p class="title">{card.title}</p>
            <p class="artist">{card.artistDisplay}</p>
        </div>
        <button
            type="button"
            aria-label="Meer over dit kunstwerk"
            onclick={expand}
        >
            <svg
                height="24"
                width="24"
                viewBox="0 -960 960 960"
                fill="currentColor"
                aria-hidden="true"
            >
                <path
                    d="M280-280h120q17 0 28.5 11.5T440-240q0 17-11.5 28.5T400-200H240q-17 0-28.5-11.5T200-240v-160q0-17 11.5-28.5T240-440q17 0 28.5 11.5T280-400v120Zm400-400H560q-17 0-28.5-11.5T520-720q0-17 11.5-28.5T560-760h160q17 0 28.5 11.5T760-720v160q0 17-11.5 28.5T720-520q-17 0-28.5-11.5T680-560v-120Z"
                />
            </svg>
        </button>
    </header>
    <div class="image">
        <ArtworkImage {src} alt={card.title} fit="contain" backdrop />
    </div>
</section>

<Modal bind:open={expanded}>
    <div class="details">
        <div class="details-image">
            <ArtworkImage {src} alt={card.title} fit="contain" backdrop />
        </div>
        <div class="details-text">
            <header class="details-title">
                <h1>{card.title}</h1>
                <p class="artist">{card.artistDisplay}</p>
            </header>
            <dl>
                {#each traits as [label, value] (label)}
                    <div>
                        <dt>{label}</dt>
                        <dd>{value}</dd>
                    </div>
                {/each}
            </dl>
            {#if description}
                <p class="description">{description}</p>
            {/if}
        </div>
    </div>
</Modal>

<style>
    .art-info {
        width: 100%;
        border-radius: 20px;
        background: var(--color-secondary);
        box-shadow: 0 2px 8px var(--primary-shadow);
        overflow: hidden;
    }

    header {
        padding: 24px;
        display: flex;
        justify-content: space-between;
        align-items: flex-start;
        gap: 12px;
    }

    .title {
        font-family: var(--font-main), serif;
        font-size: clamp(1rem, 1.5vw, 1.4rem);
        font-weight: 500;
        margin: 0 0 6px;
        color: var(--text-primary);
    }

    .artist {
        font-family: var(--font-secondary), sans-serif;
        font-size: clamp(0.8rem, 1.1vw, 1rem);
        margin: 0;
        color: var(--text-tertiary);
        white-space: pre-line;
    }

    header button {
        background: none;
        border: none;
        cursor: pointer;
        padding: 0;
        flex-shrink: 0;
        color: var(--icon-primary);
        opacity: 0.6;
        transition: opacity 180ms ease;
    }

    header button:hover,
    header button:focus-visible {
        opacity: 1;
    }

    .image {
        height: 300px;
        border-top: 1px solid var(--text-art-details);
    }

    .details {
        display: grid;
        grid-template-columns: 1fr 1fr;
        width: 60vw;
        height: 60vh;
    }

    .details-image {
        min-height: 0;
    }

    .details-text {
        display: flex;
        flex-direction: column;
        gap: 2rem;
        padding: clamp(24px, 3vw, 48px) clamp(32px, 4vw, 64px);
        min-height: 0;
        overflow: auto;
        font-family: var(--font-secondary), sans-serif;
        color: var(--text-primary);
    }

    .details-title {
        display: block;
        padding: 0 0 10px;
        border-bottom: 1px solid var(--secondary-border);
    }

    .details-title h1 {
        font-family: var(--font-main), serif;
    }

    dl {
        margin: 0;
    }

    dl div {
        display: grid;
        grid-template-columns: 140px 1fr;
        gap: 16px;
        padding: 8px 0;
        border-bottom: 1px solid var(--hub-border-bottom);
    }

    dl div:last-child {
        border-bottom: none;
    }

    dt {
        font-size: 0.8rem;
        letter-spacing: 0.06em;
        text-transform: uppercase;
        opacity: 0.55;
        align-self: center;
    }

    dd {
        margin: 0;
        align-self: center;
    }

    .description {
        line-height: 1.55;
        white-space: pre-line;
        margin: 0;
    }

    @media (max-width: 900px) {
        .details {
            grid-template-columns: 1fr;
            width: 90vw;
            height: auto;
        }

        .details-image {
            height: 40vh;
        }
    }
</style>
