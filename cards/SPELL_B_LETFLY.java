package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_B_LETFLY extends Card {

    public SPELL_B_LETFLY()
    {
        setImageSets("WX25-P1-087");

        setOriginalName("LET FLY");
        setAltNames("レットフライ Retto Furai");
        setDescription("jp",
                "あなたの＜原子＞のシグニ１体を対象とし、次の対戦相手のターン終了時まで、それは@>@U：このシグニがバニッシュされたとき、以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手の手札を１枚見ないで選び、捨てさせる。\n" +
                "$$2対戦相手のシグニ１体を対象とし、%Bを支払ってもよい。そうした場合、ターン終了時まで、それのパワーを－8000する。@@を得る。" +
                "~#：対戦相手のシグニを２体まで対象とし、それらをダウンする。"
        );

        setName("en", "LET FLY");
        setDescription("en",
                "Target 1 of your <<Atom>> SIGNI, and until the end of your opponent's next turn, it gains:" +
                "@>@U: When this SIGNI is banished, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Choose 1 card from your opponent's hand without looking, and discard it.\n" +
                "$$2 Target 1 of your opponent's SIGNI, and you may pay %B. If you do, until end of turn, it gets --8000 power.@@" +
                "~#Target up to 2 of your opponent's SIGNI, and down them."
        );

		setName("zh_simplified", "LET FLY");
        setDescription("zh_simplified", 
                "你的<<原子>>精灵1只作为对象，直到下一个对战对手的回合结束时为止，其得到\n" +
                "@>@U :当这只精灵被破坏时，从以下的2种选1种。%B。这样做的场合，直到回合结束时为止，其的力量-8000。\n" +
                "$$1 不看对战对手的手牌选1张，舍弃。\n" +
                "$$2 对战对手的精灵1只作为对象，可以支付@@" +
                "~#对战对手的精灵2只最多作为对象，将这些横置。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.BLUE);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final SpellAbility spell;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            spell = registerSpellAbility(this::onSpellEffPreTarget, this::onSpellEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withClass(CardSIGNIClass.ATOM)));
        }
        private void onSpellEff()
        {
            CardIndex target = spell.getTarget();
            if(target != null)
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.BANISH, this::onAttachedAutoEff);
                attachAbility(target, attachedAuto, ChronoDuration.nextTurnEnd(getOpponent()));
            }
        }
        private void onAttachedAutoEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex cardIndex = playerChoiceHand().get();
                getAbility().getSourceCardIndex().getIndexedInstance().discard(cardIndex);
            } else {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                
                if(target != null && payEner(Cost.color(CardColor.BLUE, 1)))
                {
                    getAbility().getSourceCardIndex().getIndexedInstance().gainPower(target, -8000, ChronoDuration.turnEnd());
                }
            }
        }

        private void onLifeBurstEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.DOWN).OP().SIGNI());
            down(data);
        }
    }
}

