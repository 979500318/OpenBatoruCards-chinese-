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
import open.batoru.data.Cost;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.AbilityORCost;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.PlayerRuleCheckModifier;

public final class PIECE_X_DontSTOP extends Card {
    
    public PIECE_X_DontSTOP()
    {
        setImageSets("WXDi-P05-005");
        
        setOriginalName("Don't STOP!");
        setAltNames("ドントストップ Donto Sutoppu Dont Stop");
        setDescription("jp",
                "このゲームの間、あなたは以下の能力を得る。" +
                "@>@C：対戦相手は追加で手札を１枚捨てるか%Xを支払わないかぎり[[ガード]]ができない。"
        );
        
        setName("en", "Don't STOP!");
        setDescription("en",
                "You gain the following ability for the duration of the game.\n" +
                "@>@C: Your opponent cannot [[Guard]] unless they additionally pay a %X or discard a card."
        );
        
        setName("en_fan", "Don't STOP!");
        setDescription("en_fan",
                "This game, you gain the following ability:" +
                "@>@C: Your opponent can't [[Guard]] unless they discard 1 card from their hand or pay %X."
        );
        
		setName("zh_simplified", "Don’t STOP!");
        setDescription("zh_simplified", 
                "这场游戏期间，你得到以下的能力。\n" +
                "@>@C :对战对手如果不追加把手牌1张舍弃或支付%X，那么不能[[防御]]。@@\n"
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
            ConstantAbility attachedConst = new ConstantAbility(new PlayerRuleCheckModifier<>(PlayerRuleCheckType.COST_TO_GUARD, TargetFilter.HINT_OWNER_OP, data ->
                new AbilityORCost(new DiscardCost(1), new EnerCost(Cost.colorless(1)))
            ));
            
            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.permanent());
        }
    }
}
