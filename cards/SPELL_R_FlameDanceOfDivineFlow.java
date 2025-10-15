package open.batoru.data.cards;

import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_R_FlameDanceOfDivineFlow extends Card {

    public SPELL_R_FlameDanceOfDivineFlow()
    {
        setImageSets("WXDi-P09-062");

        setOriginalName("神流の炎舞");
        setAltNames("カンナノエンブ Kanna no Enbu");
        setDescription("jp",
                "以下の２つから１つを選ぶ。\n" +
                "$$1あなたのトラッシュから＜武勇＞のシグニ１枚を対象とし、%Rを支払ってもよい。そうした場合、それを手札に加える。\n" +
                "$$2対戦相手のパワー10000以下のシグニ１体を対象とし、手札から＜武勇＞のシグニ１枚を捨てる。そうした場合、それをバニッシュする。"
        );

        setName("en", "Divine Dance");
        setDescription("en",
                "Choose one of the following.\n" +
                "$$1 You may pay %R. If you do, add target <<Brave>> SIGNI from your trash to your hand.\n" +
                "$$2 Discard a <<Brave>> SIGNI. If you do, vanish target SIGNI on your opponent's field with power 10000 or less."
        );
        
        setName("en_fan", "Flame Dance of Divine Flow");
        setDescription("en_fan",
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 <<Valor>> SIGNI from your trash, and you may pay %R. If you do, add it to your hand.\n" +
                "$$2 Target 1 of your opponent's SIGNI with power 10000 or less, and you may discard 1 <<Valor>> SIGNI from your hand. If you do, banish it."
        );

		setName("zh_simplified", "神流的炎舞");
        setDescription("zh_simplified", 
                "从以下的2种选1种。\n" +
                "$$1 从你的废弃区把<<武勇>>精灵1张作为对象，可以支付%R。这样做的场合，将其加入手牌。\n" +
                "$$2 对战对手的力量10000以下的精灵1只作为对象，从手牌把<<武勇>>精灵1张舍弃。这样做的场合，将其破坏。\n"
        );

        setType(CardType.SPELL);
        setColor(CardColor.RED);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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
            spell.setModeChoice(1);
        }

        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(spell.getChosenModes() == 1 ? new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.VALOR).fromTrash() :
                                                                            new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,10000)));
        }
        private void onSpellEff()
        {
            if(spell.getTarget() != null)
            {
                if(spell.getChosenModes() == 1)
                {
                    if(payEner(Cost.color(CardColor.RED, 1)))
                    {
                        addToHand(spell.getTarget());
                    }
                } else {
                    if(discard(0, 1, new TargetFilter().SIGNI().withClass(CardSIGNIClass.VALOR)).get() != null)
                    {
                        banish(spell.getTarget());
                    }
                }
            }
        }
    }
}
