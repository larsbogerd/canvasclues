<script lang="ts">
    import type { SourceKey } from '#lib/api/types.ts'
    import { ART_SOURCES } from '#lib/sources.ts'

    let { source }: { source: SourceKey } = $props()

    const museum = $derived(ART_SOURCES[source])
</script>

<!-- The logo files differ in colour (the Met's is red) but all have transparent backgrounds, so
     masking currentColor with them makes a matching set that plain CSS can mute or highlight. -->
<span
    class="museum-logo"
    role="img"
    aria-label={museum.name}
    style:mask-image="url('{museum.logo}')"
    style:-webkit-mask-image="url('{museum.logo}')"
></span>

<style>
    .museum-logo {
        display: block;
        width: 100%;
        height: 100%;
        background-color: currentColor;
        mask-size: contain;
        mask-repeat: no-repeat;
        mask-position: center;
        -webkit-mask-size: contain;
        -webkit-mask-repeat: no-repeat;
        -webkit-mask-position: center;
    }
</style>
