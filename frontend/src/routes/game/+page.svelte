<script lang="ts">
    import { goto } from '$app/navigation'
    import { resolve } from '$app/paths'
    import type { GameMode } from '#lib/api/types.ts'
    import DifficultyPicker from '#lib/components/hub/DifficultyPicker.svelte'
    import MuseumPicker from '#lib/components/hub/MuseumPicker.svelte'
    import RoleCard from '#lib/components/hub/RoleCard.svelte'
    import Footer from '#lib/components/ui/Footer.svelte'
    import PageHeader from '#lib/components/ui/PageHeader.svelte'
    import curatorImage from '#lib/images/Curator2.png'
    import visitorImage from '#lib/images/Visitor2.png'

    let difficulty = $state<GameMode>('makkelijk')
</script>

<div class="page">
    <PageHeader />
    <main>
        <h1>Kies je museum</h1>
        <MuseumPicker />
        <!-- Read with the card titles: "Speel als Curator / Bezoeker". -->
        <h2>Speel als</h2>
        <div class="roles">
            <RoleCard
                eyebrow="Creëer"
                role="Curator"
                description="Stel een nieuwe collectie samen en begeleid bezoekers met jouw hints"
                image={curatorImage}
                onclick={() =>
                    goto(
                        resolve('/game/curator/[difficulty=gameMode]', {
                            difficulty,
                        }),
                    )}
            >
                <DifficultyPicker bind:value={difficulty} />
            </RoleCard>
            <RoleCard
                eyebrow="Ontcijfer"
                role="Bezoeker"
                description="Betreed een bestaande collectie en ontcijfer de hint van de curator"
                image={visitorImage}
                onclick={() => goto(resolve('/game/bezoeker'))}
            />
        </div>
    </main>
    <Footer />
</div>

<style>
    .page {
        position: relative;
        display: flex;
        flex-direction: column;
        height: 100vh;
        overflow: hidden;
        background-color: var(--color-bg-base);
    }

    main {
        display: flex;
        flex-direction: column;
        /* "safe": when the cards stack on a small window, start at the top and scroll rather
           than centring the overflow up underneath the header. */
        justify-content: safe center;
        align-items: center;
        overflow-y: auto;
        flex: 1;
        min-height: 0;
        padding: 0 2rem 5rem;
    }

    h1 {
        text-align: center;
        font-family: var(--font-main), serif;
        margin-bottom: 1.5rem;
        font-size: clamp(3rem, 4vw, 4.5rem);
        color: var(--text-primary);
    }

    h2 {
        margin: 0 0 1rem;
        font-family: var(--font-main), serif;
        font-size: 1.6rem;
        font-weight: 400;
        color: var(--text-secondary);
    }

    .roles {
        display: flex;
        justify-content: center;
        gap: 1.25rem;
        flex-wrap: wrap;
    }
</style>
