package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_R2_NabeNoTsunaCrimsonGeneral extends Card {
    
    public SIGNI_R2_NabeNoTsunaCrimsonGeneral()
    {
        setImageSets("WXDi-P05-056", "SPDi01-75");
        
        setOriginalName("紅将　ナベノツナ");
        setAltNames("コウショウナベノツナ Koushou Nabe no Tsuna");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場に赤のシグニが３種類以上あり対戦相手のエナゾーンにカードが２枚以上ある場合、対戦相手は自分のエナゾーンからカード１枚を選びトラッシュに置く。"
        );
        
        setName("en", "Nabeno - Tsuna, Crimson General");
        setDescription("en",
                "@U: At the beginning of your attack phase, if there are three or more different red SIGNI on your field and there are two or more cards in your opponent's Ener Zone, your opponent chooses a card from their Ener Zone and puts it into their trash."
        );
        
        setName("en_fan", "Nabe no Tsuna, Crimson General");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if there are 3 or more different red SIGNI on your field and there are 2 or more cards in your opponent's ener zone, your opponent chooses 1 card from their ener zone, and puts it into the trash."
        );
        
		setName("zh_simplified", "红将 渡边纲");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上的红色的精灵在3种类以上且对战对手的能量区的牌在2张以上的场合，对战对手从自己的能量区选1张牌放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(2);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getEnerCount(getOpponent()) >= 2 &&
               new TargetFilter().own().SIGNI().withColor(CardColor.RED).getExportedData().stream().map(c -> ((CardIndex)c).getCardReference().getOriginalName()).distinct().count() >= 3)
            {
                CardIndex cardIndex = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.BURN).own().fromEner()).get();
                trash(cardIndex);
            }
        }
    }
}
