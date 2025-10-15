package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_W3_DaihouikaWaterPhantomDiva extends Card {
    
    public SIGNI_W3_DaihouikaWaterPhantomDiva()
    {
        setImageSets("WXDi-P08-043");
        
        setOriginalName("幻水歌姫　ダイホウイカ");
        setAltNames("ゲンスイウタヒメダイホウイカ Gensuiutahime Daihouika");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のシグニ１体から対象とし、手札を３枚まで捨てる。ターン終了時まで、それのパワーをこの方法で捨てたカード１枚につき－3000する。\n" +
                "@E：あなたの場に青のシグニが３体以上ある場合、カードを１枚引く。"
        );
        
        setName("en", "Daihouika, Aquatic Phantom Diva");
        setDescription("en",
                "@U: At the beginning of your attack phase, discard up to three cards. Target SIGNI on your opponent's field gets --3000 power for each card discarded this way until end of turn.\n" +
                "@E: If there are three or more blue SIGNI on your field, draw a card."
        );
        
        setName("en_fan", "Daihouika, Water Phantom Diva");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI, and you may discard up to 3 cards from your hand. If you do, until end of turn, it gets --3000 power for each card discarded this way.\n" +
                "@E: If there are 3 or more blue SIGNI on your field, draw 1 card."
        );
        
		setName("zh_simplified", "幻水歌姬 巨枪乌贼");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的精灵1只作为对象，手牌3张最多舍弃。直到回合结束时为止，其的力量依据这个方法舍弃的牌的数量，每有1张就-3000。\n" +
                "@E :你的场上的蓝色的精灵在3只以上的场合，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
        setLevel(3);
        setPower(10000);
        
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
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null)
            {
                DataTable<CardIndex> data = discard(0,3);
                
                if(data.get() != null)
                {
                    gainPower(target, -3000 * data.size(), ChronoDuration.turnEnd());
                }
            }
        }
        
        private void onEnterEff()
        {
            if(new TargetFilter().own().SIGNI().withColor(CardColor.BLUE).getValidTargetsCount() >= 3)
            {
                draw(1);
            }
        }
    }
}
