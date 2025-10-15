package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_B1_CubicZirconiaNaturalSource extends Card {
    
    public SIGNI_B1_CubicZirconiaNaturalSource()
    {
        setImageSets("WXDi-P05-063");
        setLinkedImageSets("WXDi-P02-042");
        
        setOriginalName("羅原　ＣＺ");
        setAltNames("ラゲンキュービックジルコニア Ragen Kyuubikku Jirukonia CZ");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、対戦相手はカードを１枚引き、あなたは対戦相手の手札を１枚見ないで選び、捨てさせる。/n" +
                "@E %B：あなたの場に《羅原姫　ＺｒＯ２》がある場合、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－4000する。"
        );
        
        setName("en", "CZ, Natural Element");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, your opponent draws a card and discards a card at random.\n" +
                "@E %B: If there is \"ZrO2, Natural Element Queen\" on your field, target SIGNI on your opponent's field gets --4000 power until end of turn."
        );
        
        setName("en_fan", "Cubic Zirconia, Natural Source");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, your opponent draws 1 card and you choose 1 card from your opponent's hand without looking, and discard it.\n" +
                "@E %B: If there is a \"Zirconia, Natural Source Princess\" on your field, target 1 of your opponent's SIGNI, and until end of turn, it gets --4000 power."
        );
        
		setName("zh_simplified", "罗原 CZ");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，对战对手抽1张牌，你不看对战对手的手牌选1张，舍弃。\n" +
                "@E %B:你的场上有《羅原姫　ＺｒＯ２》的场合，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-4000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ATOM);
        setLevel(1);
        setPower(2000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLUE, 1)), this::onEnterEff);
        }
        
        private void onAutoEff()
        {
            draw(getOpponent(), 1);
            
            CardIndex cardIndex = playerChoiceHand().get();
            discard(cardIndex);
        }
        
        private void onEnterEff()
        {
            if(new TargetFilter().own().SIGNI().withName("羅原姫　ＺｒＯ２").getValidTargetsCount() > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                gainPower(target, -4000, ChronoDuration.turnEnd());
            }
        }
    }
}
