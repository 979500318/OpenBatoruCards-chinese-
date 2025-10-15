package open.batoru.data.cards;

import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_K_WitchNiche extends Card {

    public SPELL_K_WitchNiche()
    {
        setImageSets("WDK04-020");

        setOriginalName("ウィッチ・ニッチ");
        setAltNames("ウィッチニッチ Wicchi Nicchi");
        setDescription("jp",
                "以下の２つから１つを選ぶ。\n" +
                "$$1対象のあなたの＜トリック＞のシグニ１体を場からトラッシュに置く。そうした場合、あなたのトラッシュから対象の＜トリック＞のシグニ１枚を手札に加える。\n" +
                "$$2【エナチャージ１】"
        );

        setName("en", "Witch Niche");
        setDescription("en",
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your <<Trick>> SIGNI, and put it into the trash. If you do, target 1 <<Trick>> SIGNI from your trash, and add it to your hand.\n" +
                "$$2 [[Ener Charge 1]]."
        );

		setName("zh_simplified", "女巫·隙间");
        setDescription("zh_simplified", 
                "从以下的2种选1种。\n" +
                "$$1 对象的你的<<トリック>>精灵1只从场上放置到废弃区。这样做的场合，从你的废弃区把对象的<<トリック>>精灵1张加入手牌。\n" +
                "$$2 [[能量填充1]]（你的牌组最上面的牌放置到能量区）\n"
        );

        setLRIGType(CardLRIGType.GUZUKO);
        setType(CardType.SPELL);
        setColor(CardColor.BLACK);

        setPlayFormat(PlayFormat.KEY);
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
            if(spell.getChosenModes() == 1)
            {
                spell.addTarget(playerTargetCard(new TargetFilter(TargetHint.TRASH).own().SIGNI().withClass(CardSIGNIClass.TRICK)).get());
                spell.addTarget(playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.TRICK).fromTrash()).get());
            }
        }
        private void onSpellEff()
        {
            if(spell.getChosenModes() == 1)
            {
                if(trash(spell.getTarget(0)))
                {
                    addToHand(spell.getTarget(1));
                }
            } else {
                enerCharge(1);
            }
        }
    }
}
