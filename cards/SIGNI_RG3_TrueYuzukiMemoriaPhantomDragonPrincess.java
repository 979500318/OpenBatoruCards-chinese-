package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class SIGNI_RG3_TrueYuzukiMemoriaPhantomDragonPrincess extends Card {

    public SIGNI_RG3_TrueYuzukiMemoriaPhantomDragonPrincess()
    {
        setImageSets("WXDi-P09-047", "WXDi-P09-047P");

        setOriginalName("幻竜姫　真・遊月//メモリア");
        setAltNames("ゲンリュウキシンユヅキメモリア Genryuukishin Yuzuki Memoria");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたのライフクロスが１枚以下で対戦相手相手のエナゾーンにカードが２枚以上ある場合、対戦相手は自分のエナゾーンからカード１枚を選びトラッシュに置く。\n" +
                "@U：このシグニがアタックしたとき、このターンにあなたの効果によって対戦相手のエナゾーンからカードが１枚以上トラッシュに置かれていた場合、%R %G %Xを支払ってもよい。そうした場合、ターン終了時まで、このシグニは【アサシン】を得る。"
        );

        setName("en", "True Yuzuki//Memoria, Dragon Queen");
        setDescription("en",
                "@U: At the beginning of your attack phase, if you have one or fewer cards in your Life Cloth and there are two or more cards in your opponent's Ener Zone, your opponent chooses a card from their Ener Zone and puts it into their trash.\n" +
                "@U: Whenever this SIGNI attacks, if one or more cards were put from your opponent's Ener Zone into their trash by your effect this turn, you may pay %R %G %X. If you do, this SIGNI gains [[Assassin]] until end of turn."
        );
        
        setName("en_fan", "True Yuzuki//Memoria, Phantom Dragon Princess");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if you have 1 or less life cloth and there are 2 or more cards in your opponent's ener zone, your opponent chooses 1 card from their ener zone and puts it into the trash.\n" +
                "@U: Whenever this SIGNI attacks, if 1 or more cards were put from your opponent's ener zone into the trash by your effect this turn, you may pay %R %G %X. If you do, until end of turn, this SIGNI gains [[Assassin]]."
        );

		setName("zh_simplified", "幻龙姬 真·游月//回忆");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的生命护甲在1张以下且对战对手的能量区的牌在2张以上的场合，对战对手从自己的能量区选1张牌放置到废弃区。\n" +
                "@U :当这只精灵攻击时，这个回合因为你的效果从对战对手的能量区把牌1张以上放置到废弃区的场合，可以支付%R%G%X。这样做的场合，直到回合结束时为止，这只精灵得到[[暗杀]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED, CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DRAGON_BEAST);
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

            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);
        }

        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(getLifeClothCount(getOwner()) <= 1 && getEnerCount(getOpponent()) >= 2)
            {
                CardIndex cardIndex = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.BURN).own().fromEner()).get();
                trash(cardIndex);
            }
        }
        
        private void onAutoEff2()
        {
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.TRASH &&
                event.getSourceAbility() != null && isOwnCard(event.getSource()) &&
                !isOwnCard(event.getCaller()) && event.getCaller().getLocation() == CardLocation.ENER) > 0 &&
               payEner(Cost.color(CardColor.RED, 1) + Cost.color(CardColor.GREEN, 1) + Cost.colorless(1)))
            {
                attachAbility(getCardIndex(), new StockAbilityAssassin(), ChronoDuration.turnEnd());
            }
        }
    }
}
