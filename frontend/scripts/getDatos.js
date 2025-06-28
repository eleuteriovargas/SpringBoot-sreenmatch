export default async function getDatos(endpoint) {
    try {
        const response = await fetch(`http://localhost:8080${endpoint}`);
        
        if (!response.ok) {
            throw new Error(`Error ${response.status}: ${response.statusText}`);
        }
        
        return await response.json();
    } catch (error) {
        console.error(`Error fetching ${endpoint}:`, error.message);
        // Retorna array vacío para que la UI no se rompa
        return []; 
    }
}

