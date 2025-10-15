package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class LRIG_K3_MuzicaVOGUE3 extends Card {
    
    public LRIG_K3_MuzicaVOGUE3()
    {
        setImageSets("WXDi-D06-004", "SPDi07-10","SPDi08-10");
        
        setOriginalName("VOGUE3 ムジカ");
        setAltNames("ボーグスリームジカ Boogu Surii Mujika");
        setDescription("jp",
                "=T ＜DIAGRAM＞\n" +
                "^C：あなたのターンの間、対戦相手の中央のシグニゾーンにあるシグニのパワーを－2000する。\n" +
                "@E：あなたのトラッシュからシグニ１枚を対象とし、それを手札に加える。\n" +
                "@A $G1 %K0：あなたの＜DIAGRAM＞のレベル１のルリグ１体を対象とし、それをルリグデッキに戻す。"
        );
        
        setName("en", "Muzica, Vogue 3");
        setDescription("en",
                "=T <<DIAGRAM>>\n" +
                "^C: During your turn, SIGNI in your opponent's center SIGNI Zone get --2000 power.\n" +
                "@E: Add target SIGNI from your trash to your hand.\n" +
                "@A $G1 %K0: Return target level one <<DIAGRAM>> LRIG on your field to your LRIG deck."
        );
        
        setName("en_fan", "Muzica, VOGUE 3");
        setDescription("en_fan",
                "=T <<DIAGRAM>>\n" +
                "^C: During your turn, the SIGNI in your opponent's center SIGNI zone gets --2000 power.\n" +
                "@E: Target 1 SIGNI from your trash, and add it to your hand.\n" +
                "@A $G1 %K0: Target 1 of your level 1 <<DIAGRAM>> LRIGs, and return it to the LRIG deck."
        );
        
		setName("zh_simplified", "VOGUE3 穆希卡");
        setDescription("zh_simplified", 
                "=T<<DIAGRAM>>\n" +
                "^C:你的回合期间，对战对手的中央的精灵区的精灵的力量-2000。\n" +
                "@E :从你的废弃区把精灵1张作为对象，将其加入手牌。\n" +
                "@A $G1 %K0:你的<<DIAGRAM>>的等级1的分身1只作为对象，将其返回分身牌组。（下面的牌在场上保留）\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MUZICA);
        setLRIGTeam(CardLRIGTeam.DIAGRAM);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 2));
        setLevel(3);
        setLimit(6);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffSharedCond, new TargetFilter().OP().SIGNI(), new PowerModifier(-2000));
            
            registerEnterAbility(this::onEnterEff);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }
        
        private ConditionState onConstEffSharedCond(CardIndex cardIndex)
        {
            return isLRIGTeam(CardLRIGTeam.DIAGRAM) && isOwnTurn() && cardIndex.getLocation() == CardLocation.SIGNI_CENTER ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromTrash()).get();
            addToHand(target);
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TOP).own().anyLRIG().withLevel(1).withLRIGTeam(CardLRIGTeam.DIAGRAM)).get();
            returnToDeck(target, DeckPosition.TOP);
        }
    }
}
