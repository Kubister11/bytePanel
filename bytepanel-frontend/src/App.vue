<script setup>
import BackgroundSpheres from "@/components/BackgroundSpheres.vue";
import NavBar from "@/components/NavBar.vue";
import SideBar from "@/components/SideBar.vue";
</script>

<template>
  <BackgroundSpheres />
  <div class="w-screen h-screen flex justify-center items-center">
    <div class="w-[98%] h-[98%] flex flex-col gap-3">
      <NavBar />
      <div class="h-full flex gap-3">
        <SideBar />
        <div class="w-full h-full">
          <RouterView />
        </div>
      </div>
    </div>
  </div>
</template>
