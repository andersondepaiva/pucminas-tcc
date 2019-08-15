<template>
    <div>
        <p>Sign-in in progress</p>
    </div>
</template>

<script>
import AuthService from '../../shared/auth/auth-service'

export default {
    async created() {
        try {
            console.log('init created callback')
            let authService = new AuthService()
            console.log('created callback')
            var result = await authService.signInRedirectCallback()
            console.log('created callback result', result)
            var returnToUrl = '/';
            if (result.state !== undefined) { returnToUrl = result.state;}
            this.$router.push({ path: returnToUrl });
        } catch (e) {
            console.log('catch created callback', e)
            this.$router.push({ name: 'Unauthorized' });
        }
    }
}
</script>