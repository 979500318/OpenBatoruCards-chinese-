package open.batoru.data.cards;

import open.batoru.core.Deck.DeckType;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_K3_DeusThree extends Card {
    
    public LRIG_K3_DeusThree()
    {
        setImageSets("WXDi-P04-013", Mask.IGNORE+"WXDi-P111");
        
        setOriginalName("デウス・スリー");
        setAltNames("デウススリー Deusu Surii");
        setDescription("jp",
                "@C：あなたのターンの間、[[ソウル]]が付いているあなたのシグニの正面のシグニのパワーを－2000する。\n" +
                "@A %K0：あなたのシグニ１体を対象とし、このルリグの下からカード１枚をそれの[[ソウル]]にする。\n" +
                "@A $G1 @[エクシード３]@：あなたのトラッシュから[ガード]を持たないシグニを３枚まで対象とし、あなたのルリグデッキにあるピース１枚をゲームから除外する。そうした場合、それらを手札に加える。"
        );
        
        setName("en", "Deus Three");
        setDescription("en",
                "@C: During your turn, the SIGNI in front of a SIGNI on your field with a [[Soul]] attached to it gets --2000 power.\n" +
                "@A %K0: Attach a card underneath this LRIG to target SIGNI on your field as a [[Soul]].\n" +
                "@A $G1 @[Exceed 3]@: Remove a PIECE in your LRIG Deck from the game. If you do, add up to three target SIGNI without a #G from your trash to your hand."
        );
        
        setName("en_fan", "Deus Three");
        setDescription("en_fan",
                "@C: During your turn, SIGNI in front of your SIGNI attached with [[Soul]] get --2000 power.\n" +
                "@A %K0: Target 1 of your SIGNI, and attach 1 card under this LRIG to it as a [[Soul]].\n" +
                "@A $G1 @[Exceed 3]@: Target up to 3 SIGNI without #G @[Guard]@ from your trash, and exclude 1 piece in your LRIG deck from the game. If you do, add them to your hand."
        );
        
		setName("zh_simplified", "迪乌斯·叁");
        setDescription("zh_simplified", 
                "@C :你的回合期间，有[[灵魂]]附加的你的精灵的正面的精灵的力量-2000。\n" +
                "@A %K0:你的精灵1只作为对象，从这只分身的下面把1张牌作为其的[[灵魂]]。\n" +
                "@A $G1 @[超越 3]@从你的废弃区把不持有#G的精灵3张最多作为对象，你的分身牌组的和音1张从游戏除外。这样做的场合，将这些加入手牌。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.DEUS);
        setLRIGTeam(CardLRIGTeam.DEUS_EX_MACHINA);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 2));
        setLevel(3);
        setLimit(6);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffSharedCond, new TargetFilter().OP().SIGNI(), new PowerModifier(-2000));
            
            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 0)), this::onActionEff1);
            act1.setCondition(this::onActionEff1Cond);
            
            ActionAbility act2 = registerActionAbility(new ExceedCost(3), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
        }
        
        private ConditionState onConstEffSharedCond(CardIndex cardIndex)
        {
            return isOwnTurn() && cardIndex.getIndexedInstance().getOppositeSIGNI() != null &&
                   cardIndex.getIndexedInstance().getOppositeSIGNI().getIndexedInstance().getCardsUnderCount(CardUnderType.ATTACHED_SOUL) > 0 ? ConditionState.OK : ConditionState.BAD;
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
            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.HAND).own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash());
            
            if(data.get() != null)
            {
                CardIndex cardIndex = searchDeck(new TargetFilter(TargetHint.EXCLUDE).own().piece(), DeckType.LRIG).get();
                
                if(cardIndex != null && reveal(cardIndex) && exclude(cardIndex))
                {
                    addToHand(data);
                }
            }
        }
    }
}
