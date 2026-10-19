<script lang="ts">
    import '#lib/styles/colors.css'
    import '#lib/styles/fonts.css'
    import type { LayoutProps } from './$types'

    let { children }: LayoutProps = $props()
</script>

{@render children()}
