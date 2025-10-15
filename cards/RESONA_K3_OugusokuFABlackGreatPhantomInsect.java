package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.UseCondition;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.events.EventMove;

public final class RESONA_K3_OugusokuFABlackGreatPhantomInsect extends Card {

    public RESONA_K3_OugusokuFABlackGreatPhantomInsect()
    {
        setImageSets("WXDi-P11-TK06");

        setOriginalName("黒大幻蟲　オウグソク【ＦＡ】");
        setAltNames("コクダイゲンチュウオウグソクフルアーマー Kokudaigenchuu Ougusoku Furu Aamaa Full Armor");
        setDescription("jp",
                "手札とエナゾーンからシグニを合計３枚トラッシュに置く\n\n" +
                "@U：このシグニが場を離れたとき、あなたのトラッシュから黒のシグニ１枚を対象とし、それを手札に加える。\n" +
                "@E：ターン終了時まで、【チャーム】が付いている対戦相手のすべてのシグニのパワーを－10000する。"
        );

        setName("en", "King Clad [FA], Giant Phantom Insect");
        setDescription("en",
                "Put three SIGNI from your hand and/or Ener Zone into your trash.\n\n" +
                "@U: When this SIGNI leaves the field, add target black SIGNI from your trash to your hand.\n" +
                "@E: All SIGNI on your opponent's field with [[Charm]] attached get --10000 power until end of turn."
        );
        
        setName("en_fan", "Ougusuku [FA], Black Great Phantom Insect");
        setDescription("en_fan",
                "Put 3 SIGNI from your hand and/or ener zone into the trash\n\n" +
                "@U: When this SIGNI leaves the field, target 1 black SIGNI from your trash, and add it to your hand.\n" +
                "@E: Until end of turn, all of your opponent's SIGNI with [[Charm]] attached to them get --10000 power."
        );
        
		setName("zh_simplified", "黑大幻虫 大王具足虫【FA】");
        setDescription("zh_simplified", 
                "[[出现条件]]主要阶段从手牌和能量区把精灵合计3张放置到废弃区\n" +
                "@U :当这只精灵离场时，从你的废弃区把黑色的精灵1张作为对象，将其加入手牌。\n" +
                "@E :直到回合结束时为止，有[[魅饰]]附加的对战对手的全部的精灵的力量-10000。\n"
        );

        setCardFlags(CardFlag.CRAFT);

        setType(CardType.RESONA);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.MISFORTUNE_INSECT);
        setLevel(3);
        setPower(12000);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            setUseCondition(UseCondition.RESONA, 3, new TargetFilter().or(new TargetFilter().fromHand(), new TargetFilter().fromEner()));

            AutoAbility auto = registerAutoAbility(GameEventId.MOVE, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(this::onEnterEff);
        }

        private ConditionState onAutoEffCond()
        {
            return !CardLocation.isSIGNI(EventMove.getDataMoveLocation()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(CardColor.BLACK).fromTrash()).get();
            addToHand(target);
        }
        
        private void onEnterEff()
        {
            gainPower(new TargetFilter().OP().SIGNI().withCardsUnder(CardUnderType.ATTACHED_CHARM).getExportedData(), -10000, ChronoDuration.turnEnd());
        }
    }
}

