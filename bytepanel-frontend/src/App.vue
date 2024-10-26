<script setup>
import BackgroundSpheres from "@/components/BackgroundSpheres.vue";
import NavBar from "@/components/NavBar.vue";
</script>

<template>
  <BackgroundSpheres />
  <div class="w-screen h-screen flex justify-center items-center">
    <div class="w-[99%] h-[98%] flex flex-col gap-3">
      <NavBar />
      <RouterView class="h-full flex gap-3"/>
    </div>
  </div>
</template>
