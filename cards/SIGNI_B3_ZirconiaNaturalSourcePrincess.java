package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityHarmony;

public final class SIGNI_B3_ZirconiaNaturalSourcePrincess extends Card {
    
    public SIGNI_B3_ZirconiaNaturalSourcePrincess()
    {
        setImageSets("WXDi-P02-042");
        
        setOriginalName("羅原姫　ＺｒＯ２");
        setAltNames("ラゲンヒメジルコニア Ragenhime Jirukonia ZrO2");
        setDescription("jp",
                "=H 黒のルリグ１体\n\n" +
                "@U：このシグニがアタックしたとき、対戦相手のシグニ１体を対象とし、%Xを支払ってもよい。そうした場合、ターン終了時まで、それのパワーを－3000する。このターンに対戦相手が手札を２枚以上捨てていた場合、代わりにターン終了時まで、それのパワーを－8000する。\n" +
                "@E %B：カードを１枚引くか、対戦相手の手札を１枚見ないで選び、捨てさせる。"
        );
        
        setName("en", "ZrO2, Natural Element Queen");
        setDescription("en",
                "=H One black LRIG\n\n" +
                "@U: Whenever this SIGNI attacks, you may pay %X. If you do, target SIGNI on your opponent's field gets --3000 power until end of turn. If your opponent has discarded two or more cards this turn, that SIGNI gets --8000 power until end of turn instead.\n" +
                "@E %B: Draw a card or your opponent discards a card at random."
        );
        
        setName("en_fan", "Zirconia, Natural Source Princess");
        setDescription("en_fan",
                "=H 1 black LRIG\n\n" +
                "@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI, and you may pay %X. If you do, until end of turn, it gets --3000 power. If your opponent discarded 2 or more cards from their hand this turn, instead until end of turn, it gets --8000 power.\n" +
                "@E %B: Draw 1 card or choose 1 card from your opponent's hand without looking, and discard it."
        );
        
		setName("zh_simplified", "罗原姬 ZrO2");
        setDescription("zh_simplified", 
                "=H黑色的分身1只\n" +
                "@U :当这只精灵攻击时，对战对手的精灵1只作为对象，可以支付%X。这样做的场合，直到回合结束时为止，其的力量-3000。这个回合对战对手把手牌2张以上舍弃过的场合，作为替代，直到回合结束时为止，其的力量-8000。\n" +
                "@E %B:抽1张牌或，不看对战对手的手牌选1张，舍弃。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ATOM);
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
            
            registerStockAbility(new StockAbilityHarmony(1, new TargetFilter().withColor(CardColor.BLACK)));
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLUE, 1)), this::onEnterEff);
        }
        
        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null && payEner(Cost.colorless(1)))
            {
                gainPower(target, GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.DISCARD && !isOwnCard(event.getCaller())) < 2 ? -3000 : -8000, ChronoDuration.turnEnd());
            }
        }
        
        private void onEnterEff()
        {
            if(playerChoiceAction(ActionHint.DRAW, ActionHint.DISCARD) == 1)
            {
                draw(1);
            } else {
                CardIndex cardIndex = playerChoiceHand().get();
                discard(cardIndex);
            }
        }
    }
}
