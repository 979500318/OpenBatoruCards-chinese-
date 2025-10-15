package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ModifiableDouble;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.modifiers.ModifiableAddedValueModifier;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.game.gfx.GFXCardTextureLayer;

public final class LRIG_K3_DeusThreeNEO extends Card {

    public LRIG_K3_DeusThreeNEO()
    {
        setImageSets("SPDi43-04", "SPDi43-04P");

        setOriginalName("デウス・スリーNEO");
        setAltNames("デウススリーネオ Deusu Surii Neo");
        setDescription("jp",
                "@A %K0：あなたのシグニ１体を対象とし、このルリグの下からカード１枚をそれの【ソウル】にする。\n" +
                "@A $G1 @[@|バーテックス|@]@ @[ルリグデッキからアーツ１枚をルリグトラッシュに置く]@：あなたのトラッシュから#Gを持たないカードを３枚まで対象とし、それらを手札に加える。このターン、あなたの効果によって対戦相手のシグニのパワーが－（マイナス）される場合、代わりに２倍－（マイナス）される。"
        );

        setName("en", "Deus Three NEO");
        setDescription("en",
                "@A %K0: Target 1 of your SIGNI, and attach 1 card under this LRIG to it as a [[Soul]].\n" +
                "@A $G1 @[@|Vertex|@]@ @[Put 1 ARTS from the LRIG deck into the LRIG trash]@: Target up to 3 cards without #G @[Guard]@ from your trash, and add them to your hand. This turn, if the power of your opponent's SIGNI would be -- (minus) by your effect, they get -- (minus) by twice as much instead."
        );

		setName("zh_simplified", "迪乌斯·叁NEO");
        setDescription("zh_simplified", 
                "@A %K0:你的精灵1只作为对象，从这只分身的下面把1张牌作为其的[[灵魂]]。\n" +
                "@A $G1 顶点:从分身牌组把必杀1张放置到分身废弃区从你的废弃区把不持有#G的牌3张最多作为对象，将这些加入手牌。这个回合，因为你的效果把对战对手的精灵的力量-（减号）的场合，作为替代，2倍-（减号）。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.DEUS);
        setLRIGTeam(CardLRIGTeam.DEUS_EX_MACHINA);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 0)), this::onActionEff1);
            act1.setCondition(this::onActionEff1Cond);

            ActionAbility act2 = registerActionAbility(new TrashCost(new TargetFilter().ARTS()), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
            act2.setName("Vertex");
        }

        private ConditionState onActionEff1Cond()
        {
            return new TargetFilter().own().under(getCardIndex()).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ATTACH).own().SIGNI().attachable(CardUnderType.ATTACHED_SOUL)).get();

            if(target != null)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.ATTACH).own().under(getCardIndex())).get();

                attach(target, cardIndex, CardUnderType.ATTACHED_SOUL);
            }
        }

        private void onActionEff2()
        {
            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.HAND).own().fromTrash().not(new TargetFilter().guard()));
            addToHand(data);
            
            ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().OP().SIGNI(),
                new ModifiableAddedValueModifier<>(this::onAttachedConstEffModGetSample, this::onAttachedConstEffModAddedValue)
            );
            GFXCardTextureLayer.attachToSharedAbility(attachedConst, cardIndex -> new GFXCardTextureLayer(cardIndex, "double_minus"));
            attachedConst.setForcedModifierUpdate(mod -> mod instanceof PowerModifier);
            
            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.turnEnd());
        }
        private ModifiableDouble onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().getPower();
        }
        private double onAttachedConstEffModAddedValue(ModifiableDouble mod, double addedValue)
        {
            return addedValue < 0 && isOwnCard(mod.getSourceAbility().getSourceCardIndex()) ? addedValue * 2 : addedValue;
        }
    }
}

