package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;

public final class LRIGA_K2_UrithBloody extends Card {

    public LRIGA_K2_UrithBloody()
    {
        setImageSets("WXDi-P10-031");

        setOriginalName("ウリス・ブラッディ");
        setAltNames("ウリスブラッディ Urisu Buraddi");
        setDescription("jp",
                "@E：あなたのトラッシュからシグニ１枚を対象とし、それを場に出す。それの@E能力は発動しない。\n" +
                "@E %K %X %X %X @[シグニ１体を場からトラッシュに置く]@：対戦相手のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Urith Bloody");
        setDescription("en",
                "@E: Put target SIGNI from your trash onto your field. The @E abilities of SIGNI put onto your field this way do not activate.\n" +
                "@E %K %X %X %X @[Put a SIGNI on your field into its owner's trash]@: Vanish target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Urith Bloody");
        setDescription("en_fan",
                "@E: Target 1 SIGNI from your trash, and put it onto the field. Its @E abilities don't activate.\n" +
                "@E %K %X %X %X @[Put 1 SIGNI from your field into the trash]@: Target 1 of your opponent's SIGNI, and banish it."
        );

		setName("zh_simplified", "乌莉丝·鲜血");
        setDescription("zh_simplified", 
                "@E 从你的废弃区把精灵1张作为对象，将其出场。其的@E能力不能发动。\n" +
                "@E %K%X %X %X精灵1只从场上放置到废弃区:对战对手的精灵1只作为对象，将其破坏。\n" +
                "（@E能力的:的左侧有费用。则可以选择不把费用支付，而不发动）\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.URITH);
        setColor(CardColor.BLACK);
        setCost(Cost.colorless(1));
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

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new AbilityCostList(
                new EnerCost(Cost.color(CardColor.BLACK, 1) + Cost.colorless(3)),
                new TrashCost(new TargetFilter().SIGNI())
            ), this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().playable().fromTrash()).get();
            putOnField(target, Enter.DONT_ACTIVATE);
        }
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            banish(target);
        }
    }
}
