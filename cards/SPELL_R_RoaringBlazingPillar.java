package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class SPELL_R_RoaringBlazingPillar extends Card {

    public SPELL_R_RoaringBlazingPillar()
    {
        setImageSets("WX24-D2-25");

        setOriginalName("轟音の炎柱");
        setAltNames("ゴウオンノホノオバシラ Gouon no Honoobashira");
        setDescription("jp",
                "対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。&E５枚以上@0代わりに対戦相手のパワー10000以下のシグニ１体を対象とし、それをバニッシュする。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Roaring Blazing Pillar");
        setDescription("en",
                "Target 1 of your opponent's SIGNI with power 8000 or less, and banish it. &E5 or more@0 Instead, target 1 of your opponent's SIGNI with power 10000 or less, and banish it." +
                "~#Target 1 of your opponent's upped SIGNI, and banish it."
        );

		setName("zh_simplified", "轰音的炎柱");
        setDescription("zh_simplified", 
                "对战对手的力量8000以下的精灵1只作为对象，将其破坏。&E5张以上@0作为替代，对战对手的力量10000以下的精灵1只作为对象，将其破坏。" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerSpellAbility(this::onSpellEff).setRecollect(5);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onSpellEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0, !getAbility().isRecollectFulfilled() ? 8000 : 10000)).get();
            banish(target);
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
            banish(target);
        }
    }
}
