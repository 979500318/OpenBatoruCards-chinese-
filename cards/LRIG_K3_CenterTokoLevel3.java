package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.TrashCost;

public final class LRIG_K3_CenterTokoLevel3 extends Card {
    
    public LRIG_K3_CenterTokoLevel3()
    {
        setImageSets("WXDi-D02-16T");
        
        setOriginalName("【センター】とこ　レベル３");
        setAltNames("センターとこレベルサン Sentaa Toko Reberu San Center Toko");
        setDescription("jp",
                "=T ＜さんばか＞\n" +
                "^A $T1 @[＜バーチャル＞のシグニ１体を場からトラッシュに置く]@：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－3000する。\n" +
                "@E：あなたのトラッシュから＜バーチャル＞のシグニを２枚まで対象とし、それらを手札に加える。"
        );
        
        setName("en", "[Center] Toko, Level 3");
        setDescription("en",
                "=T <<Sanbaka>>\n" +
                "^A $T1 @[Put a <<Virtual>> SIGNI on the field into its owner's trash]@: Target SIGNI on your opponent's field gets --3000 power until end of turn.\n" +
                "@E: Add up to 2 target <<Virtual>> SIGNI from your trash to your hand."
        );
        
        setName("en_fan", "[Center] Toko Level 3");
        setDescription("en_fan",
                "=T <<Sanbaka>>\n" +
                "^A $T1 @[Put 1 of your <<Virtual>> SIGNI from the field into the trash]@: Target 1 of your opponent's SIGNI, and until end of turn, that SIGNI gets --3000 power.\n" +
                "@E: Target up to 2 <<Virtual>> SIGNI from your trash, and add them to your hand."
        );
        
		setName("zh_simplified", "【核心】床 等级3");
        setDescription("zh_simplified", 
                "=T<<さんばか>>\n" +
                "^A$T1 <<バーチャル>>精灵1只从场上放置到废弃区:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-3000。\n" +
                "@E :从你的废弃区把<<バーチャル>>精灵2张最多作为对象，将这些加入手牌。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.TOKO);
        setLRIGTeam(CardLRIGTeam.SANBAKA);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 2));
        setLevel(3);
        setLimit(6);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            ActionAbility act = registerActionAbility(new TrashCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.VIRTUAL)), this::onActionEff);
            act.setCondition(this::onActionEffCond);
            act.setUseLimit(UseLimit.TURN, 1);
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private ConditionState onActionEffCond()
        {
            return isLRIGTeam(CardLRIGTeam.SANBAKA) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onActionEff()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(cardIndex, -3000, ChronoDuration.turnEnd());
        }
        
        private void onEnterEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).fromTrash());
            addToHand(data);
        }
    }
}
