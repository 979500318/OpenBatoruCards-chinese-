package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_W3_CenterAngeLevel3 extends Card {
    
    public LRIG_W3_CenterAngeLevel3()
    {
        setImageSets("WXDi-D02-13A");
        
        setOriginalName("【センター】アンジュ　レベル３");
        setAltNames("センターアンジュレベルサン Sentaa Anju Reberu San Center Ange");
        setDescription("jp",
                "=T ＜さんばか＞\n" +
                "^A $T1 %W0：あなたの＜バーチャル＞のシグニ１体を対象とし、ターン終了時まで、それのパワーを＋4000する。\n" +
                "@E：対戦相手のシグニ１体を対象とし、それを手札に戻す。カードを１枚引く。"
        );
        
        setName("en", "[Center] Ange, Level 3");
        setDescription("en",
                "=T <<Sanbaka>>\n" +
                "^A $T1 %W0: Target <<Virtual>> SIGNI on your field gets +4000 power until end of turn.\n" +
                "@E: Return target SIGNI on your opponent's field to its owner's hand. Draw a card."
        );
        
        setName("en_fan", "[Center] Ange Level 3");
        setDescription("en_fan",
                "=T <<Sanbaka>>\n" +
                "^A $T1 %W0: Target 1 of your <<Virtual>> SIGNI, and until the end of your turn, that SIGNI gets +4000 power.\n" +
                "@E: Target 1 of your opponent's SIGNI, and return it to their hand. Draw 1 card."
        );
        
		setName("zh_simplified", "【核心】安洁 等级3");
        setDescription("zh_simplified", 
                "=T<<さんばか>>\n" +
                "^A$T1 %W0:你的<<バーチャル>>精灵1只作为对象，直到回合结束时为止，其的力量+4000。\n" +
                "@E :对战对手的精灵1只作为对象，将其返回手牌。抽1张牌。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.ANGE);
        setLRIGTeam(CardLRIGTeam.SANBAKA);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 2));
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
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.WHITE, 0)), this::onActionEff);
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
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI().withClass(CardSIGNIClass.VIRTUAL)).get();
            gainPower(cardIndex, 4000, ChronoDuration.turnEnd());
        }
        
        private void onEnterEff()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
            addToHand(cardIndex);
            
            draw(1);
        }
    }
}
