<script lang="ts">
    import { goto } from '$app/navigation'
    import { resolve } from '$app/paths'
    import Button from '#lib/components/ui/Button.svelte'
    import Footer from '#lib/components/ui/Footer.svelte'
    import Modal from '#lib/components/ui/Modal.svelte'
    import art from '#lib/images/mainartpiece.png'

    let howItWorksOpen = $state(false)
</script>

<div class="page">
    <main>
        <div class="content">
            <h1>Canvas Clues</h1>
            <p class="tagline">
                Vind de verborgen verbanden tussen meesterwerken!
            </p>
            <div class="buttons">
                <Button
                    class="primary start"
                    onclick={() => goto(resolve('/game'))}
                    >Start een spel</Button
                >
                <Button
                    variant="secondary"
                    onclick={() => (howItWorksOpen = true)}
                    >Hoe werkt het?</Button
                >
                <Button
                    variant="secondary"
                    onclick={() => goto(resolve('/statistics'))}
                    >Statistieken</Button
                >
            </div>
        </div>
    </main>
    <img src={art} alt="" />
    <Footer />
</div>

<Modal bind:open={howItWorksOpen}>
    <div class="how-it-works">
        <h2>Introductie Canvas Clues</h2>
        <p>
            Canvas Clues is een spel gebaseerd op het populaire spel Codenames,
            maar dan in een museum vol kunst, geheimen en verborgen verbanden.
        </p>
        <h3>De twee rollen</h3>
        <p>
            In Canvas Clues kies je in de hub tussen twee rollen: Curator en
            Bezoeker.
        </p>
        <h3>Curator</h3>
        <p>
            Als Curator is het jouw uitdaging om slimme hints te bedenken voor
            de geselecteerde kunststukken op het bord.
        </p>
        <p>Kun jij meesterlijke verbanden leggen tussen kunst en woorden?</p>
        <h3>Bezoeker</h3>
        <p>
            Als Bezoeker is het jouw uitdaging om te raden welke kunststukken
            bij de hints van de Curator horen.
        </p>
        <p>Zie jij snel patronen en ontdek jij verborgen connecties?</p>
    </div>
</Modal>

<style>
    .page {
        position: relative;
        display: flex;
        height: 100vh;
        overflow: hidden;
        background-color: var(--color-bg-base);
    }

    main {
        flex: 1;
        display: flex;
        align-items: center;
        justify-content: center;
        padding: 0 2rem;
    }

    .content {
        max-width: 550px;
        display: flex;
        flex-direction: column;
        gap: 1rem;
    }

    h1 {
        font-family: var(--font-main), sans-serif;
        font-size: 5.5rem;
        font-weight: 300;
        margin: 0;
        color: var(--text-primary);
    }

    .tagline {
        font-family: var(--font-secondary), sans-serif;
        font-size: 1.5rem;
        font-weight: 300;
        margin: 0;
        color: var(--text-secondary);
    }

    .buttons {
        display: grid;
        grid-template-columns: 1fr 1fr;
        grid-template-rows: 1fr 1fr;
        gap: 0.9rem;
        margin-top: 0.75rem;
    }

    .buttons > :global(button) {
        width: 100%;
        padding: 0.6rem 1rem;
    }

    .buttons > :global(.start) {
        grid-row: 1 / span 2;
        font-size: 1.4rem;
    }

    img {
        flex: 1;
        min-width: 0;
        height: 100%;
        object-fit: cover;
    }

    .how-it-works {
        width: 28vw;
        min-width: 280px;
        padding: clamp(24px, 3vw, 48px) clamp(32px, 4vw, 64px);
        color: var(--text-primary);
    }

    .how-it-works p {
        font-family: var(--font-secondary), sans-serif;
        font-weight: 300;
    }

    @media (max-width: 900px) {
        img {
            display: none;
        }
    }
</style>
