package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_K3_CodeArtWInecellar extends Card {
    
    public SIGNI_K3_CodeArtWInecellar()
    {
        setImageSets("WXDi-P04-085");
        
        setOriginalName("コードアート　Ｗインセラー");
        setAltNames("コードアートダブリューインセラー Koodo Aato Daburyuu Inseraa Winecellar");
        setDescription("jp",
                "@U $T1：あなたがスペルを使用したとき、対戦相手のシグニを１体を対象とし、ターン終了時まで、それのパワーを－3000する。対戦相手のデッキの上からカードを２枚トラッシュに置く。"
        );
        
        setName("en", "W - Inecellar, Code: Art");
        setDescription("en",
                "@U $T1: When you use a spell, target SIGNI on your opponent's field gets --3000 power until end of turn. Put the top two cards of your opponent's deck into their trash."
        );
        
        setName("en_fan", "Code Art W Inecellar");
        setDescription("en_fan",
                "@U $T1: Whenever you use a spell, target 1 of your opponent's SIGNI, and until end of turn, it gets --3000 power. Put the top 2 cards of your opponent's deck into the trash."
        );
        
		setName("zh_simplified", "必杀代号 恒温酒柜");
        setDescription("zh_simplified", 
                "@U $T1 :当你把魔法使用时，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-3000。从对战对手的牌组上面把2张牌放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.USE_SPELL, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -3000, ChronoDuration.turnEnd());
            
            millDeck(getOpponent(), 2);
        }
    }
}
