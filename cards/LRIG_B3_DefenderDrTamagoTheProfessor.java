package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.game.FieldZone;

public final class LRIG_B3_DefenderDrTamagoTheProfessor extends Card {

    public LRIG_B3_DefenderDrTamagoTheProfessor()
    {
        setImageSets("WXDi-P15-011", "WXDi-P15-011U");

        setOriginalName("プロフェッサー　防衛者Ｄｒ．タマゴ");
        setAltNames("プロフェッサーボウエイシャドクタータマゴ Purofessaa Boueisha Dokutaa Tamago");
        setDescription("jp",
                "@E：対戦相手のルリグ１体を対象とし、それを凍結する。\n" +
                "@A $T1 @[アップ状態の＜防衛派＞のシグニ１体をダウンする]@：カードを１枚引くか、対戦相手は手札を１枚捨てる。\n" +
                "@A $G1 %B0：あなたのシグニゾーン１つに【ゲート】１つを置く。"
        );

        setName("en", "Professor! Defender Dr. Tamago");
        setDescription("en",
                "@E: Freeze target LRIG on your opponent's field.\n@A $T1 @[Down an upped <<Defense Division>> SIGNI]@: Draw a card or your opponent discards a card.\n@A $G1 %B0: Put a [[Gate]] in one of your SIGNI Zones."
        );
        
        setName("en_fan", "Defender Dr. Tamago, the Professor");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's LRIG, and freeze it.\n" +
                "@A $T1 @[Down 1 of your upped <<Defense Faction>> SIGNI]@: Draw 1 card or your opponent discards 1 card from their hand.\n" +
                "@A $G1 %B0: Put 1 [[Gate]] on 1 of your SIGNI zones."
        );

		setName("zh_simplified", "教授 防卫者Dr.玉子");
        setDescription("zh_simplified", 
                "@E :对战对手的分身1只作为对象，将其冻结。\n" +
                "@A $T1 竖直状态的<<防衛派>>精灵1只#D:抽1张牌或，对战对手把手牌1张舍弃。\n" +
                "@A $G1 %B0:你的精灵区1个放置[[大门]]1个。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.TAMAGO);
        setLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);
            
            ActionAbility act1 = registerActionAbility(new DownCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.DEFENSE_FACTION).upped()), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);

            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().anyLRIG()).get();
            freeze(target);
        }

        private void onActionEff1()
        {
            if(playerChoiceAction(ActionHint.DRAW, ActionHint.DISCARD) == 1)
            {
                draw(1);
            } else {
                discard(getOpponent(), 1);
            }
        }
        
        private void onActionEff2()
        {
            FieldZone fieldZone = playerTargetZone(new TargetFilter(TargetHint.ZONE).own().SIGNI().not(new TargetFilter().withZoneObject(CardUnderType.ZONE_GATE))).get();
            attachZoneObject(fieldZone, CardUnderType.ZONE_GATE);
        }
    }
}
