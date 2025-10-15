package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXZoneOutlineDetail;
import open.batoru.game.gfx.GFXZoneOutlineDetail.BorderPosition;
import open.batoru.game.gfx.GFXZoneOutlineDetail.BorderType;

public final class SIGNI_B2_MakiKonuri extends Card {

    public SIGNI_B2_MakiKonuri()
    {
        setImageSets("WX25-CP1-069");

        setOriginalName("小塗マキ");
        setAltNames("コヌリマキ Konuri Maki");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、手札を１枚捨ててもよい。そうした場合、このターン、あなたの青の＜ブルアカ＞のシグニが対戦相手のライフクロス１枚をクラッシュしたとき、対戦相手は手札を１枚捨てる。" +
                "~{{C：このシグニのパワーは＋4000される。"
        );

        setName("en", "Konuri Maki");

        setName("en_fan", "Maki Konuri");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may discard 1 card from your hand. If you do, this turn, whenever 1 of your blue <<Blue Archive>> SIGNI crushes 1 of your opponent's life cloth, your opponent discards 1 card from their hand." +
                "~{{C: This SIGNI gets +4000 power."
        );

		setName("zh_simplified", "小涂真纪");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，可以把手牌1张舍弃。这样做的场合，这个回合，当你的蓝色的<<ブルアカ>>精灵把对战对手的生命护甲1张击溃时，对战对手把手牌1张舍弃。\n" +
                "~{{C:这只精灵的力量+4000。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
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

            ConstantAbility cont = registerConstantAbility(new PowerModifier(4000));
            cont.getFlags().addValue(AbilityFlag.BONDED);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(discard(0,1).get() != null)
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.CRUSH, this::onAttachedAutoEff);
                attachedAuto.setCondition(this::onAttachedAutoEffCond);
                GFX.attachToAbility(attachedAuto, new GFXZoneOutlineDetail(getOpponent(), CardLocation.LIFE_CLOTH, "discard_graffiti", -40, BorderType.OUTER, BorderPosition.TOP));
                
                attachPlayerAbility(getOwner(), attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private ConditionState onAttachedAutoEffCond(CardIndex caller)
        {
            return !isOwnCard(caller) && isOwnCard(getEvent().getSource()) &&
                    CardType.isSIGNI(getEvent().getSource().getCardReference().getType()) && getEvent().getSource().getColor().matches(CardColor.BLUE) &&
                    getEvent().getSource().getSIGNIClass().matches(CardSIGNIClass.BLUE_ARCHIVE) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            discard(getOpponent(), 1);
        }
    }
}
