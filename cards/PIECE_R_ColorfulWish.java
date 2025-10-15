package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.PieceAbility;

public final class PIECE_R_ColorfulWish extends Card {
    
    public PIECE_R_ColorfulWish()
    {
        setImageSets("WXDi-P08-002");
        
        setOriginalName("Colorful Wish");
        setAltNames("カラフルウィッシュ Karafuru Uisshi");
        setDescription("jp",
                "=U =E 合計３種類以上の色を持つ\n\n" +
                "あなたの場に白のルリグがいる場合、あなたのトラッシュから[ガード]を持つシグニ１枚を対象とし、それを手札に加える。\n" +
                "その後、あなたの場に緑のルリグがいる場合、対戦相手のパワー10000以上のシグニ１体を対象とし、それをバニッシュする。\n" +
                "その後、あなたの場に黒のルリグがいる場合、あなたのトラッシュからレベル２のシグニとレベル３のシグニをそれぞれ１枚まで対象とし、それらを手札に加える。"
        );
        
        setName("en", "Colorful Wish");
        setDescription("en",
                "=U =E You have the three LRIG on your field with three or more different colors among all members.\n\n" +
                "If there is a white LRIG on your field, add target SIGNI with a #G from your trash to your hand.\n" +
                "Then, if there is a green LRIG on your field, vanish target SIGNI on your opponent's field with power 10000 or more.\n" +
                "Then, if there is a black LRIG on your field, add up to one target level two SIGNI and one target level three SIGNI from your trash to your hand."
        );
        
        setName("en_fan", "Colorful Wish");
        setDescription("en_fan",
                "=U =E with 3 or more colors among them\n\n" +
                "If there is a white LRIG on your field, target 1 #G @[Guard]@ SIGNI from your trash, and add it to your hand.\n" +
                "Then, if there is a green LRIG on your field, target 1 of your opponent's SIGNI with power 10000 or more, and banish it.\n" +
                "Then, if there is a black LRIG on your field, target up to 1 level 2 SIGNI and up to 1 level 3 SIGNI from your trash, and add them to your hand."
        );
        
		setName("zh_simplified", "Colorful Wish");
        setDescription("zh_simplified", 
                "=U=E持有合计3种类以上的颜色（你的场上的分身3只把这个条件满足）\n" +
                "你的场上有白色的分身的场合，从你的废弃区把持有#G的精灵1张作为对象，将其加入手牌。\n" +
                "然后，你的场上有绿色的分身的场合，对战对手的力量10000以上的精灵1只作为对象，将其破坏。\n" +
                "然后，你的场上有黑色的分身的场合，从你的废弃区把等级2的精灵和等级3的精灵各1张最多作为对象，将这些加入手牌。\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
        setColor(CardColor.RED);
        setCost(Cost.colorless(1));
        setUseTiming(UseTiming.MAIN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final PieceAbility piece;
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            piece = registerPieceAbility(this::onPieceEff);
            piece.setCondition(this::onPieceEffCond);
        }
        
        private ConditionState onPieceEffCond()
        {
            return getColorsCount(getLRIGs(getOwner())) >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEff()
        {
            if(new TargetFilter().own().anyLRIG().withColor(CardColor.WHITE).getValidTargetsCount() > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withState(CardStateFlag.CAN_GUARD).fromTrash()).get();
                addToHand(target);
            }
            
            if(new TargetFilter().own().anyLRIG().withColor(CardColor.GREEN).getValidTargetsCount() > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(10000, 0)).get();
                banish(target);
            }
            
            if(new TargetFilter().own().anyLRIG().withColor(CardColor.BLACK).getValidTargetsCount() > 0)
            {
                DataTable<CardIndex> data = new DataTable<>();
                
                for(int i=2;i<=3;i++)
                {
                    CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withLevel(i).fromTrash()).get();
                    if(target != null) data.add(target);
                }
                
                addToHand(data);
            }
        }
    }
}
