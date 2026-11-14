package universalmanga.domain.extension.interactor

import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.Flow
import universalmanga.domain.extension.model.ExtensionStore
import universalmanga.domain.extension.repository.ExtensionStoreRepository

@Inject
class GetExtensionStores(
    private val repository: ExtensionStoreRepository,
) {
    suspend fun get(): List<ExtensionStore> = repository.getAll()

    fun subscribe(): Flow<List<ExtensionStore>> = repository.getAllAsFlow()
}
