package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.EnterAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_R3_RoseQuartzNaturalPyroxene extends Card {
    
    public SIGNI_R3_RoseQuartzNaturalPyroxene()
    {
        setImageSets("WXDi-P01-037");
        
        setOriginalName("羅輝石　ローズクォーツ");
        setAltNames("ラキセキローズクォーツ Rakiseki Roozu Kuootsu");
        setDescription("jp",
                "=T ＜Ｎｏ　Ｌｉｍｉｔ＞\n" +
                "^E %R %R：対戦相手のパワー12000以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@U：このシグニがアタックしたとき、手札を２枚捨ててもよい。そうした場合、カードを２枚引く。"
        );
        
        setName("en", "Rose Quartz, Natural Crystal Brilliance");
        setDescription("en",
                "=T <<No Limit>>\n" +
                "^E %R %R: Vanish target SIGNI on your opponent's field with power 12000 or less.\n" +
                "@U: Whenever this SIGNI attacks, you may discard two cards. If you do, draw two cards."
        );
        
        setName("en_fan", "Rose Quartz, Natural Pyroxene");
        setDescription("en_fan",
                "=T <<No Limit>>\n" +
                "^E %R %R: Target 1 of your opponent's SIGNI with power 12000 or less, and banish it.\n" +
                "@U: Whenever this SIGNI attacks, you may discard 2 cards from your hand. If you do, draw 2 cards."
        );
        
		setName("zh_simplified", "罗辉石 薔薇石英");
        setDescription("zh_simplified", 
                "=T<<No:Limit>>\n" +
                "^E%R %R:对战对手的力量12000以下的精灵1只作为对象，将其破坏。\n" +
                "@U :当这只精灵攻击时，可以把手牌2张舍弃。这样做的场合，抽2张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
        setLevel(3);
        setPower(12000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            EnterAbility enter = registerEnterAbility(new EnerCost(Cost.color(CardColor.RED, 2)), this::onEnterEff);
            enter.setCondition(this::onEnterEffCond);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
        }
        
        private ConditionState onEnterEffCond()
        {
            return isLRIGTeam(CardLRIGTeam.NO_LIMIT) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            banish(target);
        }
        
        private void onAutoEff()
        {
            if(discard(0,2, ChoiceLogic.BOOLEAN).size() == 2)
            {
                draw(2);
            }
        }
    }
}
