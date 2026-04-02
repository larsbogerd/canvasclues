<script setup>
import ButtonBase from "@/components/ButtonBase.vue";
</script>

<template>
  <section>
    <h1>Operative fase</h1>
    <p>Als operative probeer je te ontdekken welke kunstwerken bij de hint van de spymaster horen.</p>
    <p>Klik op een kaart om je keuze te maken en bevestig je gok om het resultaat te onthullen.
    Hoe meer juiste kunstwerken je vindt, hoe meer punten jij en de spymaster verdienen.
    Maar pas op voor de assasin! Raak je deze zwarte kaart, dan is het spel meteen voorbij.
    </p>
    <ButtonBase id="button">Begrepen</ButtonBase>
  </section>
</template>

<style scoped>
h1{
  text-align: center;
}
#button{
  display: block;
  margin: 8% auto 0;
}
</style>