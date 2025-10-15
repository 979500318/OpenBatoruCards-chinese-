package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.DiscardCost;

public final class LRIGA_G2_AiyaiHideAndAttack extends Card {

    public LRIGA_G2_AiyaiHideAndAttack()
    {
        setImageSets("WXDi-P12-042");

        setOriginalName("アイヤイ　ハイドアンドアタック");
        setAltNames("アイヤイハイドアンドアタック Aiyai Haido Ando Atakku");
        setDescription("jp",
                "@E：あなたのエナゾーンからシグニ１枚を対象とし、それを場に出す。それの@E能力は発動しない。\n" +
                "@E @[手札を２枚捨てる]@：あなたのエナゾーンからシグニ１枚を対象とし、それを場に出す。それの@E能力は発動しない。"
        );

        setName("en", "Aiyai, Hide and Attack");
        setDescription("en",
                "@E: Put target SIGNI from your Ener Zone onto your field. The @E abilities of SIGNI put onto your field this way do not activate.\n@E @[Discard two cards]@: Put target SIGNI from your Ener Zone onto your field. The @E abilities of SIGNI put onto your field this way do not activate.\n"
        );
        
        setName("en_fan", "Aiyai Hide and Attack");
        setDescription("en_fan",
                "@E: Target 1 SIGNI from your ener zone, and put it onto the field. Its @E abilities don't activate.\n" +
                "@E @[Discard 2 cards from your hand]@: Target 1 SIGNI from your ener zone, and put it onto the field. Its @E abilities don't activate."
        );
        
		setName("zh_simplified", "艾娅伊 伪装突袭");
        setDescription("zh_simplified", 
                "@E 从你的能量区把精灵1张作为对象，将其出场。其的@E能力不能发动。\n" +
                "@E :手牌2张舍弃从你的能量区把精灵1张作为对象，将其出场。其的@E能力不能发动。\n" +
                "（@E能力的:的左侧有费用。则可以选择不把费用支付，而不发动）\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.AIYAI);
        setColor(CardColor.GREEN);
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);
            registerEnterAbility(new DiscardCost(2), this::onEnterEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().fromEner().playable()).get();
            putOnField(target, Enter.DONT_ACTIVATE);
        }
    }
}
