package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.AbilityORCost;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.modifiers.PlayerRuleCheckModifier;

public final class PIECE_X_GardenOfSingularity extends Card {
    
    public PIECE_X_GardenOfSingularity()
    {
        setImageSets("WXDi-P06-006", "SPDi29-03");
        
        setOriginalName("Garden of Singularity");
        setAltNames("ガーデンオブシンギュラリティ Gaaden Obu Shingyurariti");
        setDescription("jp",
                "カードを１枚引く。このゲームの間、あなたは以下の能力を得る。" +
                "@>@C：あなたが【ガード】する際、#Gを持つカードを１枚捨てる代わりに手札を３枚捨ててもよい。"
        );
        
        setName("en", "Garden of Singularity");
        setDescription("en",
                "Draw a card. You gain the following ability for the duration of the game.\n" +
                "@>@C: As you [[Guard]], you may discard three cards instead of discarding a card with a #G."
        );
        
        setName("en_fan", "Garden of Singularity");
        setDescription("en_fan",
                "Draw 1 card. This game, you gain the following ability:" +
                "@>@C: You may [[Guard]] by discarding 3 cards from your hand instead of discarding 1 card with #G @[Guard]@."
        );
        
		setName("zh_simplified", "Garden of Singularity");
        setDescription("zh_simplified", 
                "抽1张牌。这场游戏期间，你得到以下的能力。\n" +
                "@>@C 你[[防御]]时，把持有#G的牌1张舍弃，作为替代，可以把手牌3张舍弃。@@\n"
        );

        setType(CardType.PIECE);
        setUseTiming(UseTiming.MAIN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerPieceAbility(this::onPieceEff);
        }
        
        private void onPieceEff()
        {
            draw(1);
            
            ConstantAbility attachedConst = new ConstantAbility(new PlayerRuleCheckModifier<>(PlayerRuleCheckType.COST_TO_GUARD, TargetFilter.HINT_OWNER_OWN, data ->
                new AbilityORCost(AbilityORCost.REPLACE_DEFAULT, new DiscardCost(0,3, ChoiceLogic.BOOLEAN))
            ));
            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.permanent());
        }
    }
}
