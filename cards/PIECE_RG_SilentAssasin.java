package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.PieceAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class PIECE_RG_SilentAssasin extends Card {
    
    public PIECE_RG_SilentAssasin()
    {
        setImageSets("WXDi-P01-002");
        
        setOriginalName("SILENT ASSASSIN");
        setAltNames("サイレントアサシン Sairento Asashin");
        setDescription("jp",
                "=U あなたの場に赤と緑のルリグがいる\n\n" +
                "あなたのシグニ２体までを対象とし、ターン終了時まで、それらは@>@C：このシグニは、正面にパワー12000以上のシグニがあるかぎり、[[アサシン]]を得る。@@を得る。"
        );
        
        setName("en", "Silent Assassin");
        setDescription("en",
                "=U You have a red LRIG and a green LRIG on your field.\n\n" +
                "Until end of turn, up to two target SIGNI on your field gain@>@C: As long as the SIGNI in front of this SIGNI has power 12000 or more, this SIGNI gains [[Assassin]]."
        );
        
        setName("en_fan", "SILENT ASSASIN");
        setDescription("en_fan",
                "=U There is a red and a green LRIG on your field\n\n" +
                "Target up to 2 of your SIGNI, and until end of turn, they gain:\n" +
                "@>@C: As long as there is a SIGNI with power 12000 or more in front of this SIGNI, this SIGNI gains [[Assassin]]."
        );
        
		setName("zh_simplified", "SILENT ASSASIN");
        setDescription("zh_simplified", 
                "=U你的场上有红色和绿色的分身\n" +
                "你的精灵2只最多作为对象，直到回合结束时为止，这些得到\n" +
                "@>@C :这只精灵的，正面有力量12000以上的精灵时，得到[[暗杀]]。@@\n"
        );

        setType(CardType.PIECE);
        setColor(CardColor.RED, CardColor.GREEN);
        setCost(Cost.colorless(4));
        setUseTiming(UseTiming.MAIN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final PieceAbility piece;
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            piece = registerPieceAbility(this::onPieceEffPreTarget, this::onPieceEff);
            piece.setCondition(this::onPieceEffCond);
        }
        
        private ConditionState onPieceEffCond()
        {
            if(new TargetFilter().own().anyLRIG().withColor(CardColor.RED).getValidTargetsCount() == 0 ||
               new TargetFilter().own().anyLRIG().withColor(CardColor.GREEN).getValidTargetsCount() == 0) return ConditionState.BAD;
            
            return getSIGNICount(getOwner()) > 0 ? ConditionState.OK : ConditionState.WARN;
        }
        private void onPieceEffPreTarget()
        {
            piece.setTargets(playerTargetCard(0,2, new TargetFilter(TargetHint.ABILITY).own().SIGNI()));
        }
        private void onPieceEff()
        {
            if(piece.getTarget() != null)
            {
                for(int i=0;i<piece.getTargets().size();i++)
                {
                    ConstantAbility attachedConst = new ConstantAbility(new AbilityGainModifier(this::onAttachedConstEffModGetSample));
                    attachedConst.setCondition(this::onAttachedConstEffCond);
                    
                    attachAbility(piece.getTarget(i), attachedConst, ChronoDuration.turnEnd());
                }
            }
        }
        private ConditionState onAttachedConstEffCond(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().getOppositeSIGNI() != null &&
                   cardIndex.getIndexedInstance().getOppositeSIGNI().getIndexedInstance().getPower().getValue() >= 12000 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityAssassin());
        }
    }
}
