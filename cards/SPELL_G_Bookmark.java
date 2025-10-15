package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.SpellAbility;

import java.util.List;

public final class SPELL_G_Bookmark extends Card {

    public SPELL_G_Bookmark()
    {
        setImageSets("WXDi-P10-070");

        setOriginalName("枝折");
        setAltNames("シオリ Shiori");
        setDescription("jp",
                "あなたのトラッシュからそれぞれ共通する色を持たず無色ではないシグニ２枚を対象とし、それらをエナゾーンに置く。\n\n" +
                "@U：あなたのターンの間、このカードが捨てられたとき、シグニ１体を対象とし、ターン終了時まで、それのパワーを＋2000する。" +
                "~#：対戦相手のパワー7000以上のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Bookmark");
        setDescription("en",
                "Put two target non-colorless SIGNI from your trash that do not share a color into the Ener Zone.\n\n" +
                "@U: During your turn, when this card is discarded, target SIGNI gets +2000 power until end of turn." +
                "~#Vanish target SIGNI on your opponent's field with power 7000 or more."
        );
        
        setName("en_fan", "Bookmark");
        setDescription("en_fan",
                "Target up to 2 non-colorless SIGNI that don't share a common color from your trash, and put them into the ener zone.\n\n" +
                "@U: During your turn, when you discard this card, target 1 SIGNI, and until end of turn, it gets +2000 power." +
                "~#Target 1 of your opponent's SIGNI with power 7000 or more, and banish it."
        );

		setName("zh_simplified", "枝折");
        setDescription("zh_simplified", 
                "从你的废弃区把不持有共通颜色的不是无色的精灵2张作为对象，将这些放置到能量区。\n" +
                "@U :你的回合期间，当这张牌被舍弃时，精灵1只作为对象，直到回合结束时为止，其的力量+2000。" +
                "~#对战对手的力量7000以上的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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

            AutoAbility auto = registerAutoAbility(GameEventId.DISCARD, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(0,2, new TargetFilter(TargetHint.ENER).SIGNI().withColor().fromTrash(), this::onSpellEffPreTargetCond));
        }
        private boolean onSpellEffPreTargetCond(List<CardIndex> listPickedCards)
        {
            return listPickedCards.size() == 1 ||
                   listPickedCards.size() == 2 && !listPickedCards.get(0).getIndexedInstance().getColor().matches(listPickedCards.get(1).getIndexedInstance().getColor());
        }
        private void onSpellEff()
        {
            putInEner(spell.getTargets());
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).SIGNI()).get();
            gainPower(target, 2000, ChronoDuration.turnEnd());
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(7000,0)).get();
            banish(target);
        }
    }
}
