<script setup>
import ButtonBase from "@/components/global-components/ButtonBase.vue";
</script>

<template>
  <section>
    <h1 class="modal-title">Operative fase</h1>
    <div class="modal-body">
      <p>Als operative probeer je te ontdekken welke kunstwerken bij de hint van de spymaster horen.</p>
      <p>Klik op een kaart om je keuze te maken en bevestig je gok om het resultaat te onthullen.
      Hoe meer juiste kunstwerken je vindt, hoe meer punten jij en de spymaster verdienen.
      Maar pas op voor de assasin! Raak je deze zwarte kaart, dan is het spel meteen voorbij.</p>
    </div>
    <div class="modal-actions">
      <ButtonBase>Begrepen</ButtonBase>
    </div>
  </section>
</template>

<style scoped>
section{
  width: 28vw;
  padding: clamp(24px, 3vw, 48px) clamp(32px, 4vw, 64px);
}
</style>