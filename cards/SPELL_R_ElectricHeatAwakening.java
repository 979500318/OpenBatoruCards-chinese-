package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_R_ElectricHeatAwakening extends Card {

    public SPELL_R_ElectricHeatAwakening()
    {
        setImageSets("WX25-P2-079");

        setOriginalName("電熱の覚醒");
        setAltNames("デンネツノカクセイ Dennetsu no Kakusei");
        setDescription("jp",
                "あなたの＜電機＞のシグニ１体を対象とし、ターン終了時まで、それを覚醒状態にする。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Electric Heat Awakening");
        setDescription("en",
                "Target 1 of your <<Electric Machine>> SIGNI, and until end of turn, it awakens." +
                "~#Target 1 of your opponent's upped SIGNI, and banish it."
        );

		setName("zh_simplified", "电热的觉醒");
        setDescription("zh_simplified", 
                "你的<<電機>>精灵1只作为对象，直到回合结束时为止，将其变为觉醒状态。" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.RED);

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
            spell.setTargets(playerTargetCard(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.ELECTRIC_MACHINE)));
        }
        private void onSpellEff()
        {
            CardIndex target = spell.getTarget();
            if(target != null)
            {
                gainValue(target, target.getIndexedInstance().getCardStateFlags(), CardStateFlag.AWAKENED, ChronoDuration.turnEnd());
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
            banish(target);
        }
    }
}
