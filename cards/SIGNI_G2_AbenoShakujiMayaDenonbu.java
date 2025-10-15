package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_G2_AbenoShakujiMayaDenonbu extends Card {

    public SIGNI_G2_AbenoShakujiMayaDenonbu()
    {
        setImageSets("WXDi-P14-087");

        setOriginalName("電音部　安倍=シャクジ=摩耶");
        setAltNames("デンオンブアベノシャクジマヤ Denonbu Abeno Shakuji Maya");
        setDescription("jp",
                "@U $T1：あなたの他の＜電音部＞のシグニが対戦相手のライフクロス１枚をクラッシュしたとき、【エナチャージ１】をする。"
        );

        setName("en", "DEN-ON-BU Maya-Shakuji-Abeno");
        setDescription("en",
                "@U $T1: When another <<DEN-ON-BU>> SIGNI on your field crushes one of your opponent's Life Cloth, [[Ener Charge 1]]. "
        );
        
        setName("en_fan", "Abeno-Shakuji-Maya, Denonbu");
        setDescription("en_fan",
                "@U $T1: When your other <<Denonbu>> SIGNI crushes 1 of your opponent's life cloth, [[Ener Charge 1]]."
        );

		setName("zh_simplified", "电音部 安倍=石神=摩耶");
        setDescription("zh_simplified", 
                "@U $T1 :当你的其他的<<電音部>>精灵把对战对手的生命护甲1张击溃时，[[能量填充1]]。（你的牌组最上面的牌放置到能量区）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DENONBU);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.CRUSH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(getEvent().getSourceCardIndex()) && getEvent().getSourceCardIndex().getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.DENONBU) &&
                   getEvent().getSourceCardIndex() != getCardIndex() && !isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            enerCharge(1);
        }
    }
}
