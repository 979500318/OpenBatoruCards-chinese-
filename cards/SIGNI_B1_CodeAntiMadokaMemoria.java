package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXCardTextureLayer;
import open.batoru.game.gfx.GFXTextureCardCanvas;

public final class SIGNI_B1_CodeAntiMadokaMemoria extends Card {

    public SIGNI_B1_CodeAntiMadokaMemoria()
    {
        setImageSets("WXDi-P10-060", "WXDi-P10-060P", "SPDi01-94");

        setOriginalName("コードアンチ　マドカ//メモリア");
        setAltNames("コードアンチマドカメモリア Koodo Anchi Madoka Memoria");
        setDescription("jp",
                "@U $T2：あなたの効果によって対戦相手が手札を１枚捨てたとき、手札を１枚捨ててもよい。そうした場合、カードを１枚引くか【エナチャージ１】をする。" +
                "~#：対戦相手のルリグかシグニ１体を対象とする。このターン、それがアタックしたとき、対戦相手が手札を３枚捨てないかぎり、そのアタックを無効にする。"
        );

        setName("en", "Madoka//Memoria, Code: Anti");
        setDescription("en",
                "@U $T2: Whenever your opponent discards a card by your effect, you may discard a card. If you do, draw a card or [[Ener Charge 1]]." +
                "~#Whenever target LRIG or SIGNI on your opponent's field attacks this turn, negate that attack unless your opponent discards three cards."
        );
        
        setName("en_fan", "Code Anti Madoka Memoria");
        setDescription("en_fan",
                "@U $T2: Whenever your opponent discards 1 card from their hand by your effect, you may discard 1 card from your hand. If you do, draw 1 card or [[Ener Charge 1]]." +
                "~#Target 1 of your opponent's LRIG or SIGNI. This turn, whenever it attacks, disable that attack unless your opponent discards 3 cards from their hand."
        );

		setName("zh_simplified", "古兵代号 円//回忆");
        setDescription("zh_simplified", 
                "@U $T2 :当因为你的效果对战对手把手牌1张舍弃时，可以把手牌1张舍弃。这样做的场合，抽1张牌或[[能量填充1]]。" +
                "~#对战对手的分身或精灵1只作为对象。这个回合，当其攻击时，如果对战对手不把手牌3张舍弃，那么那次攻击无效。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANCIENT_WEAPON);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.DISCARD, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 2);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return !isOwnCard(caller) && getEvent().getSourceAbility() != null && isOwnCard(getEvent().getSourceCardIndex()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(discard(0,1).get() != null)
            {
                if(playerChoiceAction(ActionHint.DRAW, ActionHint.ENER) == 1)
                {
                    draw(1);
                } else {
                    enerCharge(1);
                }
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().fromField()).get();
            
            if(target != null)
            {
                ChronoRecord record = new ChronoRecord(target, ChronoDuration.turnEnd());
                GFX.attachToChronoRecord(record, new GFXCardTextureLayer(target, new GFXTextureCardCanvas("border/discard", 0.75,3)));
                addCardRuleCheck(CardRuleCheckType.COST_TO_LAND_ATTACK, target, record, data -> new DiscardCost(0,3, ChoiceLogic.BOOLEAN));
            }
        }
    }
}
