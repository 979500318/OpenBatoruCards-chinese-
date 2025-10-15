package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_K_DarkFang extends Card {

    public SPELL_K_DarkFang()
    {
        setImageSets("WX25-P2-110");

        setOriginalName("ダーク・ファング");
        setAltNames("ダークファング Daaku Fangu");
        setDescription("jp",
                "あなたのトラッシュから＜電機＞のシグニ１枚を対象とし、それを場に出す。ターン終了時まで、それは@>@U：このシグニがアタックしたとき、以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手は手札を２枚捨てる。\n" +
                "$$2対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－5000する。@@を得る。@@" +
                "~#：あなたのトラッシュから#Gを持たないシグニ１枚を対象とし、それを手札に加えるか場に出す。"
        );

        setName("en", "Dark Fang");
        setDescription("en",
                "Target 1 <<Electric Machine>> SIGNI from your trash, and put it onto the field. Until end of turn, it gains:" +
                "@>@U: Whenever this SIGNI attacks, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Your opponent discards 2 cards from their hand.\n" +
                "$$2 Target 1 of your opponent's SIGNI, and until end of turn, it gets --5000 power.@@" +
                "~#Target 1 SIGNI without #G @[Guard]@ from your trash, and add it to your hand or put it onto the field."
        );

		setName("zh_simplified", "黑暗·尖牙");
        setDescription("zh_simplified", 
                "从你的废弃区把<<電機>>精灵1张作为对象，将其出场。直到回合结束时为止，其得到\n" +
                "@>@U :当这只精灵攻击时，从以下的2种选1种。\n" +
                "$$1 对战对手把手牌2张舍弃。\n" +
                "$$2 对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-5000。@@" +
                "~#从你的废弃区把不持有#G的精灵1张作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1) + Cost.color(CardColor.BLUE, 1));

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
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.ELECTRIC_MACHINE).fromTrash().playable()));
        }
        private void onSpellEff()
        {
            CardIndex target = spell.getTarget();
            if(putOnField(target))
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                attachAbility(target, attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private void onAttachedAutoEff()
        {
            if(playerChoiceMode() == 1)
            {
                getAbility().getSourceCardIndex().getIndexedInstance().discard(getOpponent(), 2);
            } else {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                getAbility().getSourceCardIndex().getIndexedInstance().gainPower(target, -5000, ChronoDuration.turnEnd());
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter().own().SIGNI().not(new TargetFilter().guard()).fromTrash()).get();
            
            if(target != null)
            {
                if(playerChoiceAction(ActionHint.HAND, ActionHint.FIELD) == 1)
                {
                    addToHand(target);
                } else {
                    putOnField(target);
                }
            }
        }
    }
}
