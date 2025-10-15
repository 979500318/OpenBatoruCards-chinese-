package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.cost.ExceedCost;

public final class SPELL_R_ScytheOfTheFlameGod extends Card {

    public SPELL_R_ScytheOfTheFlameGod()
    {
        setImageSets("WXDi-P10-057");

        setOriginalName("炎神の大鎌");
        setAltNames("エンジンノオオガマ Enjin no Oogama");
        setDescription("jp",
                "このスペルを使用する際、使用コストとして追加でエクシード７を支払ってもよい。\n\n" +
                "対戦相手のパワー2000以下のシグニ１体を対象とし、それをバニッシュする。追加でエクシード７を支払っていた場合、代わりに対戦相手のパワー12000以下のシグニ１体を対象とし、それをバニッシュする。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Vulcanic Scythe");
        setDescription("en",
                "As you use this spell, you may pay Exceed 7 as an additional use cost. \n\n" +
                "Vanish target SIGNI on your opponent's field with power 2000 or less. If you paid the Exceed 7, instead vanish target SIGNI on your opponent's field with power 12000 or less." +
                "~#Vanish target upped SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Scythe of the Flame God");
        setDescription("en_fan",
                "While using this spell, you may pay an additional @[Exceed 7]@ for its use cost.\n\n" +
                "Target 1 of your opponent's SIGNI with power 2000 or less, and banish it. If you paid an additional @[Exceed 7]@, instead target 1 of your opponent's SIGNI with power 12000 or less, and banish it." +
                "~#Target 1 of your opponent's upped SIGNI, and banish it."
        );

		setName("zh_simplified", "炎神的大镰");
        setDescription("zh_simplified", 
                "这张魔法使用时，可以作为使用费用追加把超越7支付。（从你的分身的下面把牌合计7张放置到分身废弃区）\n" +
                "对战对手的力量2000以下的精灵1只作为对象，将其破坏。追加把超越7支付过的场合，作为替代，对战对手的力量12000以下的精灵1只作为对象，将其破坏。" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

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
            spell.setAdditionalCost(new ExceedCost(7));

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0, !spell.hasPaidAdditionalCost() ? 2000 : 12000)));
        }
        private void onSpellEff()
        {
            banish(spell.getTarget());
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
            banish(target);
        }
    }
}
