package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_B1_RonoveAzureDevil extends Card {
    
    public SIGNI_B1_RonoveAzureDevil()
    {
        setImageSets("WXDi-P03-065", "SPDi38-30");
        
        setOriginalName("蒼魔　ロノウェ");
        setAltNames("ソウマロノウェ Souma Ronoue");
        setDescription("jp",
                "@U：あなたのターン終了時、このターンにあなたのデッキからカードが３枚以上トラッシュに置かれていた場合、カードを１枚引く。" +
                "~#：対戦相手のシグニ１体を対象とし、それをダウンし凍結する。カードを１枚引く。"
        );
        
        setName("en", "Ronove, Azure Evil");
        setDescription("en",
                "@U: At the end of your turn, if three or more cards were put from your deck into your trash this turn, draw a card." +
                "~#Down target SIGNI on your opponent's field and freeze it. Draw a card."
        );
        
        setName("en_fan", "Ronove, Azure Devil");
        setDescription("en_fan",
                "@U: At the end of your turn, if 3 or more cards were put from your deck into the trash this turn, draw 1 card." +
                "~#Target 1 of your opponent's SIGNI, and down and freeze it. Draw 1 card."
        );
        
		setName("zh_simplified", "苍魔 柏诺贝");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，这个回合从你的牌组把牌3张以上放置到废弃区的场合，抽1张牌。" +
                "~#对战对手的精灵1只作为对象，将其横置并冻结。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(1);
        setPower(2000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(GameLog.getTurnRecordsCount(event ->
               event.getId() == GameEventId.TRASH && isOwnCard(event.getCaller()) && event.getCaller().isEffectivelyAtLocation(CardLocation.DECK_MAIN)) >= 3)
            {
                draw(1);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.DOWN).OP().SIGNI()).get();
            down(target);
            freeze(target);
            
            draw(1);
        }
    }
}
