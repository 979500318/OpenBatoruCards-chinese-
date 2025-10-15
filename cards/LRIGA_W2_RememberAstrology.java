package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class LRIGA_W2_RememberAstrology extends Card {

    public LRIGA_W2_RememberAstrology()
    {
        setImageSets("WXDi-P15-034");

        setOriginalName("リメンバ・アストロジー");
        setAltNames("リメンバアストロジー Rimenba Asutorojii");
        setDescription("jp",
                "@E：以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@C：アタックできない。@@を得る。\n" +
                "$$2対戦相手のシグニ１体を対象とし、%W %Xを支払ってもよい。そうした場合、それを手札に戻す。"
        );

        setName("en", "Remember Astrology");
        setDescription("en",
                "@E: Choose one of the following.\n$$1Target SIGNI on your opponent's field gains@>@C: This SIGNI cannot attack.@@until end of turn.\n$$2You may pay %W %X. If you do, return target SIGNI on your opponent's field to its owner's hand."
        );
        
        setName("en_fan", "Remember Astrology");
        setDescription("en_fan",
                "@E: @[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI, and until end of turn, it gains:" +
                "@>@C: Can't attack.@@" +
                "$$2 Target 1 of your opponent's SIGNI, and you may pay %W %X. If you do, return it to their hand."
        );

		setName("zh_simplified", "忆·星占");
        setDescription("zh_simplified", 
                "@E :从以下的2种选1种。\n" +
                "$$1 对战对手的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n" +
                "$$2 对战对手的精灵1只作为对象，可以支付%W%X。这样做的场合，将其返回手牌。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.REMEMBER);
        setColor(CardColor.WHITE);
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);
        }

        private void onEnterEff()
        {
            CardIndex target;
            if(playerChoiceMode() == 1)
            {
                target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
                if(target != null) attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
            } else {
                target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
                
                if(target != null && payEner(Cost.color(CardColor.WHITE, 1) + Cost.colorless(1)))
                {
                    addToHand(target);
                }
            }
        }
    }
}
