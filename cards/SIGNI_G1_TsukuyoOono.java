package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_G1_TsukuyoOono extends Card {

    public SIGNI_G1_TsukuyoOono()
    {
        setImageSets("WXDi-CP02-084");

        setOriginalName("大野ツクヨ");
        setAltNames("オオノツクヨ Oono Tsukuyo");
        setDescription("jp",
                "@A #D：次の対戦相手のターン終了時まで、このシグニのパワーを＋4000し、このシグニは@>@U $T1：あなたのライフクロス１枚がクラッシュされたとき、あなたのデッキの一番上を公開する。そのカードが＜ブルアカ＞の場合、【エナチャージ１】をする。@@を得る。" +
                "~{{C：このシグニのパワーは＋4000される。"
        );

        setName("en", "Ono Tsukuyo");
        setDescription("en",
                "@A #D: This SIGNI gets +4000 power and gains@>@U $T1: When one of your Life Cloth is crushed, reveal the top card of your deck. If that card is <<Blue Archive>>, [[Ener Charge 1]].@@until the end of your opponent's next end phase.~{{C: This SIGNI gets +4000 power."
        );
        
        setName("en_fan", "Tsukuyo Oono");
        setDescription("en_fan",
                "@A #D: Until the end of your opponent's next turn, this SIGNI gets +4000 power, and it gains:" +
                "@>@U $T1: When 1 of your life cloth is crushed, reveal the top card of your deck. If it is a <<Blue Archive>> card, [[Ener Charge 1]].@@" +
                "~{{C: This SIGNI gets +4000 power."
        );

		setName("zh_simplified", "大野月咏");
        setDescription("zh_simplified", 
                "@A 横置:直到下一个对战对手的回合结束时为止，这只精灵的力量+4000，这只精灵得到\n" +
                "@>@U $T1 :当你的生命护甲1张被击溃时，你的牌组最上面公开。那张牌是<<ブルアカ>>的场合，[[能量填充1]]。@@\n" +
                "~{{C:这只精灵的力量+4000。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerActionAbility(new DownCost(), this::onActionEff);
            
            ConstantAbility cont = registerConstantAbility(new PowerModifier(4000));
            cont.getFlags().addValue(AbilityFlag.BONDED);
        }

        private void onActionEff()
        {
            gainPower(getCardIndex(), 4000, ChronoDuration.nextTurnEnd(getOpponent()));

            AutoAbility attachedAuto = new AutoAbility(GameEventId.CRUSH, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            attachedAuto.setUseLimit(UseLimit.TURN, 1);
            
            attachAbility(getCardIndex(), attachedAuto, ChronoDuration.nextTurnEnd(getOpponent()));
        }
        private ConditionState onAttachedAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            CardIndex cardIndex = reveal();
            
            if(cardIndex == null || !cardIndex.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.BLUE_ARCHIVE) || enerCharge(1).get() == null)
            {
                returnToDeck(cardIndex, DeckPosition.TOP);
            }
        }
    }
}
